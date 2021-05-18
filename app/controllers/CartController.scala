package controllers

import models.Cart
import play.api.mvc._
import repositories.{ CRUDRepository, CartRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class CartController @Inject() (
  cartRepository: CartRepository,
  cc:             MessagesControllerComponents
)(implicit ec:    ExecutionContext)
    extends CRUDController[Cart](cc) {
  override val indexRedirect: Call                 = routes.CartController.index()
  override val repository:    CRUDRepository[Cart] = cartRepository
}
