package controllers

import models.User
import play.api.libs.json._
import play.api.mvc._
import repositories.UserRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class UserController @Inject() (cc: ControllerComponents, val userRepository: UserRepository)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of users")
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
            userRepository.api.create(input).map { user =>
              Ok(Json.toJson(user))
            }
          }
        )
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"User $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"User $id removed")
    }
}
