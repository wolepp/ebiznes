package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class CartController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of carts")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Cart $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Cart created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Cart $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Cart $id removed")
    }
}
