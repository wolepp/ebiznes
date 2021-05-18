package controllers

import models.OrderItem
import play.api.mvc._
import repositories.{ CRUDRepository, OrderItemRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class OrderItemController @Inject() (
  cc:                  MessagesControllerComponents,
  orderItemRepository: OrderItemRepository
)(implicit ec:         ExecutionContext)
    extends CRUDController[OrderItem](cc) {
  override val indexRedirect: Call                      = routes.OrderItemController.index()
  override val repository:    CRUDRepository[OrderItem] = orderItemRepository
}
