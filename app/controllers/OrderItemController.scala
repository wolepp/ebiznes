package controllers

import models.OrderItem
import play.api.mvc._
import repositories.OrderItemRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class OrderItemController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: OrderItemRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[OrderItem](orderRepository, routes.OrderItemController.index(), cc)
