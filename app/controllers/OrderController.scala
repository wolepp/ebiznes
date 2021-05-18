package controllers

import models.Order
import play.api.mvc._
import repositories.OrderRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class OrderController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: OrderRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[Order](orderRepository, routes.OrderController.index(), cc)
