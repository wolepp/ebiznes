package controllers

import models.Payment
import play.api.mvc._
import repositories.PaymentRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class PaymentController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: PaymentRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[Payment](orderRepository, routes.PaymentController.index(), cc)
