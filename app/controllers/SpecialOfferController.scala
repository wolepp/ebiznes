package controllers

import models.SpecialOffer
import play.api.mvc._
import repositories.SpecialOfferRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class SpecialOfferController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: SpecialOfferRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[SpecialOffer](orderRepository, routes.SpecialOfferController.index(), cc)
