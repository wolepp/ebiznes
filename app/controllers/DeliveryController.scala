package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class DeliveryController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of deliveries")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Delivery $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Delivery created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Delivery $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Delivery $id removed")
    }
}
