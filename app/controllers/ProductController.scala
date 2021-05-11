package controllers

import models.Product
import play.api.data.Form
import play.api.data.Forms.{ mapping, nonEmptyText, number }
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.ProductRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class ProductController @Inject() (
  cc:                MessagesControllerComponents,
  productRepository: ProductRepository
)(implicit ec:       ExecutionContext)
    extends MessagesAbstractController(cc) {

  case class CreateProductForm(categoryId: Int, name: String, description: String)

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      productRepository.api
        .list()
        .map(products => {
          Ok(Json.toJson(products))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      productRepository.api
        .get(id)
        .map {
          case Some(product) => Ok(Json.toJson(product))
          case None          => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Product]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            productRepository.api.create(input).map { _ =>
              Redirect(routes.ProductController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val productResult = request.body.validate[Product]
        productResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          productData => {
            productRepository.api
              .update(productData.insertId(id))
              .map(product => {
                Ok(Json.toJson(product))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      productRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Product $id deleted")
      }
    }

  val productForm: Form[CreateProductForm] = Form {
    mapping(
      "categoryId"  -> number,
      "name"        -> nonEmptyText,
      "description" -> nonEmptyText
    )(CreateProductForm.apply)(CreateProductForm.unapply)
  }

  // FORMS

//  def listForm() =
//    Action.async { implicit request =>
//      {
//        productRepository.api
//          .list()
//          .map(products => {
//            Ok(views.html.product.listView(products))
//          })
//      }
//    }
}
