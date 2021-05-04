package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class OrderController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of orders")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Order $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Order created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Order $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Order $id removed")
    }
}
