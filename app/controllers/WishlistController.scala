package controllers

import models.Wishlist
import play.api.mvc._
import repositories.WishlistRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class WishlistController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: WishlistRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[Wishlist](orderRepository, routes.WishlistController.index(), cc)
