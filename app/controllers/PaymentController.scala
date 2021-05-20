package controllers

import models.Payment
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional, sqlTimestamp }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, PaymentRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class PaymentController @Inject() (
  cc:                MessagesControllerComponents,
  paymentRepository: PaymentRepository
)(implicit ec:       ExecutionContext)
    extends CRUDController[Payment](cc) {
  val indexRedirect: Call                    = routes.PaymentController.index()
  val repository:    CRUDRepository[Payment] = paymentRepository

  val form: Form[Payment] = Form {
    mapping(
      "id"        -> optional(number),
      "status"    -> number,
      "updatedAt" -> sqlTimestamp
    )(Payment.apply)(Payment.unapply)
  }

  val formListRedirect: Call = routes.PaymentController.formList()

  def listView(entities: Seq[Payment]): Html = views.html.payment_list(entities)

  def createView(form: Form[Payment])(implicit request: MessagesRequest[_]): Html = views.html.payment_create(form)

  def updateView(id: Int, form: Form[Payment])(implicit request: MessagesRequest[_]): Html =
    views.html.payment_update(id, form)
}
