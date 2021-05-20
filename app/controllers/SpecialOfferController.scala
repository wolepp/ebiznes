package controllers

import models.SpecialOffer
import play.api.data.Form
import play.api.data.Forms.{ mapping, nonEmptyText, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, SpecialOfferRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class SpecialOfferController @Inject() (
  cc:                     MessagesControllerComponents,
  specialOfferRepository: SpecialOfferRepository
)(implicit ec:            ExecutionContext)
    extends CRUDController[SpecialOffer](cc) {
  val indexRedirect: Call                         = routes.SpecialOfferController.index()
  val repository:    CRUDRepository[SpecialOffer] = specialOfferRepository

  val form: Form[SpecialOffer] = Form {
    mapping(
      "id"       -> optional(number),
      "name"     -> nonEmptyText,
      "discount" -> number
    )(SpecialOffer.apply)(SpecialOffer.unapply)
  }

  val formListRedirect: Call = routes.SpecialOfferController.formList()

  def listView(entities: Seq[SpecialOffer]): Html = views.html.specialoffer_list(entities)

  def createView(form: Form[SpecialOffer])(implicit request: MessagesRequest[_]): Html = views.html.specialoffer_create(form)

  def updateView(id: Int, form: Form[SpecialOffer])(implicit request: MessagesRequest[_]): Html =
    views.html.specialoffer_update(id, form)
}
