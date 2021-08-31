package controllers

import models.utils.CRUDModel
import play.api.data.Form
import play.api.libs.json.{ JsValue, Json, OFormat }
import play.api.mvc._
import play.twirl.api.Html
import repositories.CRUDRepository

import scala.concurrent.{ ExecutionContext, Future }

abstract class CRUDController[M <: CRUDModel[M]](
  cc: MessagesControllerComponents
)(implicit
  format: OFormat[M],
  ec:     ExecutionContext
) extends MessagesAbstractController(cc) {

  val indexRedirect: Call
  val repository:    CRUDRepository[M]

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
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
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
      repository.api.delete(Some(id)).map {
        case None   => NotFound(Json.obj("error" -> "Not found"))
        case entity => Ok(Json.toJson(entity))
      }
    }

  // FORMS

  val form: Form[M]

  val formListRedirect: Call

  def listView(entities: Seq[M]): Html

  def createView(form: Form[M])(implicit request: MessagesRequest[_]): Html

  def updateView(id: Int, form: Form[M])(implicit request: MessagesRequest[_]): Html

  def deleteView(id: Int, form: Form[M])(implicit request: MessagesRequest[_]): Html

  def formList: Action[AnyContent] =
    Action.async { implicit request =>
      {
        repository.api
          .list()
          .map(entities => Ok(listView(entities)))
      }
    }

  def formCreate(): Action[AnyContent] =
    Action.async { implicit request =>
      {
        val entities = repository.api.list()
        entities.map(_ => Ok(createView(form)))
      }
    }

  def formCreateHandler(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        form
          .bindFromRequest()
          .fold(
            errorForm => {
              Future.successful(BadRequest(createView(errorForm)))
            },
            input => {
              repository.api
                .create(input.copyWithoutId)
                .map(_ => { Redirect(formListRedirect) })
            }
          )
      }
    }

  def formUpdate(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        val entity = repository.api.get(id)
        entity.map(e => {
          val updateForm = form.fill(e.get)
          Ok(updateView(id, updateForm))
        })
      }
    }

  def formUpdateHandler(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        form
          .bindFromRequest()
          .fold(
            errorForm => {
              Future.successful(BadRequest(updateView(-1, errorForm)))
            },
            input => {
              repository.api.update(input).map(_ => Redirect(formListRedirect))
            }
          )
      }
    }

  def formDelete(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        val entity = repository.api.get(id)
        entity.map(e => {
          val deleteForm = form.fill(e.get)
          Ok(deleteView(id, deleteForm))
        })
      }
    }

  def formDeleteHandler(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        form
          .bindFromRequest()
          .fold(
            errorForm => {
              Future.successful(BadRequest(deleteView(-1, errorForm)))
            },
            input => {
              repository.api.delete(input.id).map(_ => Redirect(formListRedirect))
            }
          )
      }
    }

}
