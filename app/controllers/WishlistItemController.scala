package controllers

import models.WishlistItem
import play.api.data.Form
import play.api.data.Forms.{ mapping, number, optional }
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, WishlistItemRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class WishlistItemController @Inject() (
  cc:                     MessagesControllerComponents,
  wishlistItemRepository: WishlistItemRepository
)(implicit ec:            ExecutionContext)
    extends CRUDController[WishlistItem](cc) {
  val indexRedirect: Call                         = routes.WishlistItemController.index()
  val repository:    CRUDRepository[WishlistItem] = wishlistItemRepository

  val form: Form[WishlistItem] = Form {
    mapping(
      "id"         -> optional(number),
      "wishlistId" -> number,
      "productId"  -> number
    )(WishlistItem.apply)(WishlistItem.unapply)
  }

  val formListRedirect: Call = routes.WishlistItemController.formList()

  def listView(entities: Seq[WishlistItem]): Html = views.html.wishlistitem_list(entities)

  def createView(form: Form[WishlistItem])(implicit request: MessagesRequest[_]): Html = views.html.wishlistitem_create(form)

  def updateView(id: Int, form: Form[WishlistItem])(implicit request: MessagesRequest[_]): Html =
    views.html.wishlistitem_update(id, form)

  def deleteView(id: Int, form: Form[WishlistItem])(implicit request: MessagesRequest[_]): Html =
    views.html.wishlistitem_delete(id, form)
}
