package controllers

import models.Product
import play.api.mvc._
import repositories.ProductRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class ProductController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: ProductRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[Product](orderRepository, routes.ProductController.index(), cc)
