package controllers

import models.Cart
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, CartRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class CartController @Inject() (
  cc:             MessagesControllerComponents,
  cartRepository: CartRepository
)(implicit ec:    ExecutionContext)
    extends CRUDController[Cart](cc) {
  val indexRedirect: Call                 = routes.CartController.index()
  val repository:    CRUDRepository[Cart] = cartRepository

  val form: Form[Cart] = Form {
    mapping(
      "id"        -> optional(number),
      "userId"    -> number,
      "productId" -> number,
      "discount"  -> number,
      "quantity"  -> number
    )(Cart.apply)(Cart.unapply)
  }

  val formListRedirect: Call = routes.CartController.formList()

  def listView(entities: Seq[Cart]): Html = views.html.cart_list(entities)

  def createView(form: Form[Cart])(implicit request: MessagesRequest[_]): Html = views.html.cart_create(form)

  def updateView(id: Int, form: Form[Cart])(implicit request: MessagesRequest[_]): Html = views.html.cart_update(id, form)
}
