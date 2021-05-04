package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class UserController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of users")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"User $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("User created")
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
