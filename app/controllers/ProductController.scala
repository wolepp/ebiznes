package controllers

import models.Product
import play.api.mvc._
import repositories.{ CRUDRepository, ProductRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class ProductController @Inject() (
  cc:                MessagesControllerComponents,
  productRepository: ProductRepository
)(implicit ec:       ExecutionContext)
    extends CRUDController[Product](cc) {
  override val indexRedirect: Call                    = routes.ProductController.index()
  override val repository:    CRUDRepository[Product] = productRepository
}
