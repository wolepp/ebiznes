package controllers

import models.SpecialOffer
import play.api.mvc._
import repositories.{ CRUDRepository, SpecialOfferRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class SpecialOfferController @Inject() (
  cc:                     MessagesControllerComponents,
  specialOfferRepository: SpecialOfferRepository
)(implicit ec:            ExecutionContext)
    extends CRUDController[SpecialOffer](cc) {
  override val indexRedirect: Call                         = routes.SpecialOfferController.index()
  override val repository:    CRUDRepository[SpecialOffer] = specialOfferRepository
}
