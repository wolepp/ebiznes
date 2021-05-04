package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class SpecialOfferController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of special offers")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Special offer $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Special offer created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Special offer $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Special offer $id removed")
    }
}
