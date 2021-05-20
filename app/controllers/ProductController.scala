package controllers

import models.Product
import play.api.data.Form
import play.api.data.Forms.{ mapping, nonEmptyText, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, ProductRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class ProductController @Inject() (
  cc:                MessagesControllerComponents,
  productRepository: ProductRepository
)(implicit ec:       ExecutionContext)
    extends CRUDController[Product](cc) {
  val indexRedirect: Call                    = routes.ProductController.index()
  val repository:    CRUDRepository[Product] = productRepository

  val form: Form[Product] = Form {
    mapping(
      "id"          -> optional(number),
      "categoryId"  -> number,
      "name"        -> nonEmptyText,
      "description" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  }

  val formListRedirect: Call = routes.ProductController.formList()

  def listView(entities: Seq[Product]): Html = views.html.product_list(entities)

  def createView(form: Form[Product])(implicit request: MessagesRequest[_]): Html = views.html.product_create(form)

  def updateView(id: Int, form: Form[Product])(implicit request: MessagesRequest[_]): Html =
    views.html.product_update(id, form)
}
