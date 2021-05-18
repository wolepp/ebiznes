package controllers

import models.Delivery
import play.api.mvc._
import repositories.{ CRUDRepository, DeliveryRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class DeliveryController @Inject() (
  cc:                 MessagesControllerComponents,
  deliveryRepository: DeliveryRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Delivery](cc) {
  override val indexRedirect: Call                     = routes.DeliveryController.index()
  override val repository:    CRUDRepository[Delivery] = deliveryRepository
}
