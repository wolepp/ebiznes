package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class CategoryController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of categories")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Category $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Category created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Category $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Category $id removed")
    }
}
