package controllers

import models.utils.CRUDModel
import play.api.libs.json.{ JsValue, Json, OFormat }
import play.api.mvc._
import repositories.CRUDRepository

import scala.concurrent.{ ExecutionContext, Future }

abstract class CRUDController[M <: CRUDModel[M]](
  repository:    CRUDRepository[M],
  indexRedirect: Call,
  cc:            MessagesControllerComponents
)(implicit
  format: OFormat[M],
  ec:     ExecutionContext
) extends MessagesAbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async {
      repository.api
        .list()
        .map(entity => {
          Ok(Json.toJson(entity))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      repository.api
        .get(id)
        .map {
          case Some(entity) => Ok(Json.toJson(entity))
          case None         => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[M]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            repository.api.create(input).map { _ =>
              Redirect(indexRedirect)
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val result = request.body.validate[M]
        result.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          data => {
            repository.api
              .update(data.insertId(id))
              .map(product => {
                Ok(Json.toJson(product))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      repository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"${repository.modelName} $id deleted")
      }
    }

}
