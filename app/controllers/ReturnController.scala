package controllers

import models.Return
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, ReturnRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class ReturnController @Inject() (
  cc:               MessagesControllerComponents,
  returnRepository: ReturnRepository
)(implicit ec:      ExecutionContext)
    extends CRUDController[Return](cc) {
  val indexRedirect: Call                   = routes.ReturnController.index()
  val repository:    CRUDRepository[Return] = returnRepository

  val form: Form[Return] = Form {
    mapping(
      "id"      -> optional(number),
      "userId"  -> number,
      "orderId" -> number,
      "status"  -> number
    )(Return.apply)(Return.unapply)
  }

  val formListRedirect: Call = routes.ReturnController.formList()

  def listView(entities: Seq[Return]): Html = views.html.return_list(entities)

  def createView(form: Form[Return])(implicit request: MessagesRequest[_]): Html = views.html.return_create(form)

  def updateView(id: Int, form: Form[Return])(implicit request: MessagesRequest[_]): Html =
    views.html.return_update(id, form)
}
