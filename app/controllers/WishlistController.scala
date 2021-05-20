package controllers

import models.Wishlist
import play.api.data.Form
import play.api.data.Forms.{ mapping, nonEmptyText, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, WishlistRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class WishlistController @Inject() (
  cc:                 MessagesControllerComponents,
  wishlistRepository: WishlistRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Wishlist](cc) {
  val indexRedirect: Call                     = routes.WishlistController.index()
  val repository:    CRUDRepository[Wishlist] = wishlistRepository

  val form: Form[Wishlist] = Form {
    mapping(
      "id"     -> optional(number),
      "userId" -> number,
      "name"   -> nonEmptyText
    )(Wishlist.apply)(Wishlist.unapply)
  }

  val formListRedirect: Call = routes.WishlistController.formList()

  def listView(entities: Seq[Wishlist]): Html = views.html.wishlist_list(entities)

  def createView(form: Form[Wishlist])(implicit request: MessagesRequest[_]): Html = views.html.wishlist_create(form)

  def updateView(id: Int, form: Form[Wishlist])(implicit request: MessagesRequest[_]): Html =
    views.html.wishlist_update(id, form)
}
