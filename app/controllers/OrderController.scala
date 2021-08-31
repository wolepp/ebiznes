package controllers

import models.Order
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, OrderRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class OrderController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: OrderRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[Order](cc) {
  override val indexRedirect: Call                  = routes.OrderController.index()
  override val repository:    CRUDRepository[Order] = orderRepository

  val form: Form[Order] = Form {
    mapping(
      "id"         -> optional(number),
      "userId"     -> number,
      "deliveryId" -> optional(number),
      "paymentId"  -> optional(number)
    )(Order.apply)(Order.unapply)
  }

  val formListRedirect: Call = routes.OrderController.formList()

  def listView(entities: Seq[Order]): Html = views.html.order_list(entities)

  def createView(form: Form[Order])(implicit request: MessagesRequest[_]): Html = views.html.order_create(form)

  def updateView(id: Int, form: Form[Order])(implicit request: MessagesRequest[_]): Html =
    views.html.order_update(id, form)

  def deleteView(id: Int, form: Form[Order])(implicit request: MessagesRequest[_]): Html =
    views.html.order_delete(id, form)
}
