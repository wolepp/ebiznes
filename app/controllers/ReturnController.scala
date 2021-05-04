package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class ReturnController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of return")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Return $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Return created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Return $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Return $id removed")
    }
}
