package controllers

import models.Payment
import play.api.mvc._
import repositories.{ CRUDRepository, PaymentRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class PaymentController @Inject() (
  cc:                MessagesControllerComponents,
  paymentRepository: PaymentRepository
)(implicit ec:       ExecutionContext)
    extends CRUDController[Payment](cc) {
  override val indexRedirect: Call                    = routes.PaymentController.index()
  override val repository:    CRUDRepository[Payment] = paymentRepository
}
