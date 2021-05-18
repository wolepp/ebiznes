package controllers

import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repositories.UserRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class UserController @Inject() (
  cc:          MessagesControllerComponents,
  repository:  UserRepository
)(implicit ec: ExecutionContext)
    extends CRUDController[User](repository, routes.UserController.index(), cc) {

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
        repository.api
          .list()
          .map(users => {
            Ok(views.html.user_list(users))
          })
      }
    }

  def createUser(): Action[AnyContent] =
    Action.async { implicit request =>
      {
        val users = repository.api.list()
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
              repository.api
                .create(user.copy(id = None))
                .map(_ => { Redirect(routes.UserController.listUsers) })
            }
          )
      }
    }

  def updateUser(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      {
        val user = repository.api.get(id)
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
              repository.api.update(user).map(_ => Redirect(routes.UserController.listUsers))
            }
          )
      }
    }

  def deleteUser(id: Int): Action[AnyContent] =
    Action { implicit request: MessagesRequest[AnyContent] =>
      {
        repository.api.delete(id)
        Redirect(routes.UserController.listUsers)
      }
    }
}
