package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class ProductController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of product")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Product $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Product created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Product $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Product $id removed")
    }
}
