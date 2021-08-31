package controllers

import models.CartItem
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, CartItemRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class CartItemController @Inject() (
  cc:             MessagesControllerComponents,
  cartRepository: CartItemRepository
)(implicit ec:    ExecutionContext)
    extends CRUDController[CartItem](cc) {
  val indexRedirect: Call                     = routes.CartItemController.index()
  val repository:    CRUDRepository[CartItem] = cartRepository

  val form: Form[CartItem] = Form {
    mapping(
      "id"        -> optional(number),
      "userId"    -> number,
      "productId" -> number,
      "quantity"  -> number
    )(CartItem.apply)(CartItem.unapply)
  }

  val formListRedirect: Call = routes.CartItemController.formList()

  def listView(entities: Seq[CartItem]): Html = views.html.cartitem_list(entities)

  def createView(form: Form[CartItem])(implicit request: MessagesRequest[_]): Html = views.html.cartitem_create(form)

  def updateView(id: Int, form: Form[CartItem])(implicit request: MessagesRequest[_]): Html =
    views.html.cartitem_update(id, form)

  def deleteView(id: Int, form: Form[CartItem])(implicit request: MessagesRequest[_]): Html =
    views.html.cartitem_delete(id, form)
}
