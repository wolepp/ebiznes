package controllers

import models.Return
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.ReturnRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class ReturnController @Inject() (cc: ControllerComponents, returnRepository: ReturnRepository)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      returnRepository.api
        .list()
        .map(returns => {
          Ok(Json.toJson(returns))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      returnRepository.api
        .get(id)
        .map {
          case Some(ret) => Ok(Json.toJson(ret))
          case None      => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Return]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            returnRepository.api.create(input).map { _ =>
              Redirect(routes.ReturnController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val returnResult = request.body.validate[Return]
        returnResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          returnData => {
            returnRepository.api
              .update(returnData.insertId(id))
              .map(ret => {
                Ok(Json.toJson(ret))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      returnRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Return $id deleted")
      }
    }
}
