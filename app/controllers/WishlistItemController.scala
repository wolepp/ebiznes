package controllers

import models.WishlistItem
import play.api.mvc._
import repositories.{ CRUDRepository, WishlistItemRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class WishlistItemController @Inject() (
  cc:                     MessagesControllerComponents,
  wishlistItemRepository: WishlistItemRepository
)(implicit ec:            ExecutionContext)
    extends CRUDController[WishlistItem](cc) {
  override val indexRedirect: Call                         = routes.WishlistItemController.index()
  override val repository:    CRUDRepository[WishlistItem] = wishlistItemRepository
}
