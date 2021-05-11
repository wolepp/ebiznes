package controllers

import models.Category
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.CategoryRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class CategoryController @Inject() (cc: ControllerComponents, categoryRepository: CategoryRepository)(implicit
  ec:                                   ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      categoryRepository.api
        .list()
        .map(categories => {
          Ok(Json.toJson(categories))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      categoryRepository.api
        .get(id)
        .map {
          case Some(category) => Ok(Json.toJson(category))
          case None           => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Category]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            categoryRepository.api.create(input).map { _ =>
              Redirect(routes.CategoryController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val categoryResult = request.body.validate[Category]
        categoryResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          categoryData => {
            categoryRepository.api
              .update(categoryData.insertId(id))
              .map(category => {
                Ok(Json.toJson(category))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      categoryRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Category $id deleted")
      }
    }
}
