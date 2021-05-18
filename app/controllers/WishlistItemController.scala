package controllers

import models.WishlistItem
import play.api.mvc._
import repositories.WishlistItemRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class WishlistItemController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: WishlistItemRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[WishlistItem](orderRepository, routes.WishlistItemController.index(), cc)
