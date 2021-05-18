package controllers

import models.Category
import play.api.mvc._
import repositories.CategoryRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class CategoryController @Inject() (
  cc:                 MessagesControllerComponents,
  categoryRepository: CategoryRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Category](categoryRepository, routes.CategoryController.index(), cc)
