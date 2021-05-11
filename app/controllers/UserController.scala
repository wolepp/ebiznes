package controllers

import models.User
import play.api.data.Form
import play.api.data.Forms.{ ignored, mapping, nonEmptyText, number, optional }
import play.api.libs.json._
import play.api.mvc._
import repositories.UserRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class UserController @Inject() (cc: MessagesControllerComponents, val userRepository: UserRepository)(implicit
  ec:                               ExecutionContext
) extends MessagesAbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      userRepository.api
        .list()
        .map(users => {
          Ok(Json.toJson(users))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      userRepository.api.get(id).map {
        case Some(user) => Ok(Json.toJson(user))
        case None       => NotFound(Json.obj("error" -> "Not Found"))
      }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[User]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            userRepository.api.create(input).map { _ =>
              Redirect(routes.UserController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val userResult = request.body.validate[User]
        userResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          userData => {
            userRepository.api
              .update(userData.insertId(id))
              .map(user => {
                Ok(Json.toJson(user))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      userRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Product $id deleted")
      }
    }

  // FORMS

  val form: Form[User] = Form {
    mapping(
      "id"       -> optional(number),
      "name"     -> nonEmptyText,
      "email"    -> nonEmptyText,
      "password" -> nonEmptyText,
      "city"     -> nonEmptyText,
      "address"  -> nonEmptyText
    )(User.apply)(User.unapply)
  }

  def listUsers: Action[AnyContent] =
    Action.async { implicit request =>
      {
        userRepository.api
          .list()
          .map(users => {
            Ok(views.html.user_list(users))
          })
      }
    }

  def createUser(): Action[AnyContent] =
    Action.async { implicit request =>
      {
        val users = userRepository.api.list()
        users.map(_ => Ok(views.html.user_create(form)))
      }
    }

  def createUserHandler(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        form
          .bindFromRequest()
          .fold(
            errorForm => {
              Future.successful(BadRequest(views.html.user_create(errorForm)))
            },
            user => {
              userRepository.api
                .create(user.copy(id = None))
                .map(_ => { Redirect(routes.UserController.listUsers) })
            }
          )
      }
    }

  def updateUser(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        val user = userRepository.api.get(id)
        user.map(u => {
          val updateForm = form.fill(u.get)
          Ok(views.html.user_update(id, updateForm))
        })
      }
    }

  def updateUserHandler(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        form
          .bindFromRequest()
          .fold(
            errorForm => {
              Future.successful(BadRequest(views.html.user_update(-1, errorForm)))
            },
            user => {
              userRepository.api.update(user).map(_ => Redirect(routes.UserController.listUsers))
            }
          )
      }
    }

  def deleteUser(id: Int): Action[AnyContent] =
    Action { implicit request: MessagesRequest[AnyContent] =>
      {
        userRepository.api.delete(id)
        Redirect(routes.UserController.listUsers)
      }
    }
}
