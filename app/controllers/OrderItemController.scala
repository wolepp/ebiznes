package controllers

import models.OrderItem
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, OrderItemRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class OrderItemController @Inject() (
  cc:                  MessagesControllerComponents,
  orderItemRepository: OrderItemRepository
)(implicit ec:         ExecutionContext)
    extends CRUDController[OrderItem](cc) {
  val indexRedirect: Call                      = routes.OrderItemController.index()
  val repository:    CRUDRepository[OrderItem] = orderItemRepository

  val form: Form[OrderItem] = Form {
    mapping(
      "id"        -> optional(number),
      "orderId"   -> number,
      "productId" -> number,
      "quantity"  -> number
    )(OrderItem.apply)(OrderItem.unapply)
  }

  val formListRedirect: Call = routes.OrderItemController.formList()

  def listView(entities: Seq[OrderItem]): Html = views.html.orderitem_list(entities)

  def createView(form: Form[OrderItem])(implicit request: MessagesRequest[_]): Html = views.html.orderitem_create(form)

  def updateView(id: Int, form: Form[OrderItem])(implicit request: MessagesRequest[_]): Html =
    views.html.orderitem_update(id, form)

  def deleteView(id: Int, form: Form[OrderItem])(implicit request: MessagesRequest[_]): Html =
    views.html.orderitem_delete(id, form)
}
