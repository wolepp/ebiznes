package controllers

import models.Delivery
import play.api.mvc._
import repositories.DeliveryRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class DeliveryController @Inject() (
  cc:                 MessagesControllerComponents,
  deliveryRepository: DeliveryRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Delivery](deliveryRepository, routes.DeliveryController.index(), cc)
