package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class WishlistController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of wishlist")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Wishlist $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Wishlist created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Wishlist $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Wishlist $id removed")
    }
}
