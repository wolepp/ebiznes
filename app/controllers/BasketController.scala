package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class BasketController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of baskets")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Basket $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Basket created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Basket $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Basket $id removed")
    }
}
