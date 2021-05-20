package controllers

import models.Delivery
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional, sqlTimestamp }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, DeliveryRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class DeliveryController @Inject() (
  cc:                 MessagesControllerComponents,
  deliveryRepository: DeliveryRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Delivery](cc) {
  val indexRedirect: Call                     = routes.DeliveryController.index()
  val repository:    CRUDRepository[Delivery] = deliveryRepository

  val form: Form[Delivery] = Form {
    mapping(
      "id"             -> optional(number),
      "status"         -> number,
      "shippingMethod" -> number,
      "deliveryDate"   -> sqlTimestamp
    )(Delivery.apply)(Delivery.unapply)
  }

  val formListRedirect: Call = routes.DeliveryController.formList()

  def listView(entities: Seq[Delivery]): Html = views.html.delivery_list(entities)

  def createView(form: Form[Delivery])(implicit request: MessagesRequest[_]): Html = views.html.delivery_create(form)

  def updateView(id: Int, form: Form[Delivery])(implicit request: MessagesRequest[_]): Html =
    views.html.delivery_update(id, form)
}
