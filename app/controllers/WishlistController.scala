package controllers

import models.Wishlist
import play.api.mvc._
import repositories.{ CRUDRepository, WishlistRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class WishlistController @Inject() (
  cc:                 MessagesControllerComponents,
  wishlistRepository: WishlistRepository
)(implicit ec:        ExecutionContext)
    extends CRUDController[Wishlist](cc) {
  override val indexRedirect: Call                     = routes.WishlistController.index()
  override val repository:    CRUDRepository[Wishlist] = wishlistRepository
}
