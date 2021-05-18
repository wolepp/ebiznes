package controllers

import models.Order
import play.api.mvc._
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
}
