package controllers

import models.Cart
import play.api.mvc._
import repositories.CartRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class CartController @Inject() (
  cartRepository: CartRepository,
  cc:             MessagesControllerComponents
)(implicit ec:    ExecutionContext)
    extends CRUDController[Cart](cartRepository, routes.CartController.index(), cc)
