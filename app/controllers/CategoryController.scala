package controllers

import models.Category
import play.api.mvc._
import repositories.{ CRUDRepository, CategoryRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class CategoryController @Inject() (
  cc:                 MessagesControllerComponents,
  categoryRepository: CategoryRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Category](cc) {
  override val indexRedirect: Call                     = routes.CategoryController.index()
  override val repository:    CRUDRepository[Category] = categoryRepository
}
