package controllers

import models.{ Category, User }
import play.api.data.Form
import play.api.data.Forms.{ mapping, nonEmptyText, number, optional }
import play.api.mvc._
import play.twirl.api.{ BaseScalaTemplate, Html }
import repositories.{ CRUDRepository, CategoryRepository }

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class CategoryController @Inject() (
  cc:                 MessagesControllerComponents,
  categoryRepository: CategoryRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Category](cc) {
  val indexRedirect: Call                     = routes.CategoryController.index()
  val repository:    CRUDRepository[Category] = categoryRepository

  val form: Form[Category] = Form {
    mapping(
      "id"               -> optional(number),
      "name"             -> nonEmptyText,
      "parentCategoryId" -> optional(number)
    )(Category.apply)(Category.unapply)
  }

  def template(id: Int, form: Form[User])(implicit request: MessagesRequest[AnyContent]): Html =
    views.html.user_update(id, form)

  val formListRedirect: Call = routes.CategoryController.formList()

  def listView(entities: Seq[Category]): Html = views.html.category_list(entities)

  def createView(form: Form[Category])(implicit request: MessagesRequest[_]): Html = views.html.category_create(form)

  def updateView(id: Int, form: Form[Category])(implicit request: MessagesRequest[_]): Html =
    views.html.category_update(id, form)

  def deleteView(id: Int, form: Form[Category])(implicit request: MessagesRequest[_]): Html =
    views.html.category_delete(id, form)
}
