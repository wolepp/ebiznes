package repositories

import models.Cart
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class CartRepository @Inject() (
  dbConfigProvider:      DatabaseConfigProvider,
  val userRepository:    UserRepository,
  val productRepository: ProductRepository
)(implicit ec:           ExecutionContext)
    extends CRUDRepository[Cart](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class CartTable(tag: Tag) extends BaseTable(tag) {
    def userId    = column[Int]("user_id")
    def productId = column[Int]("product_id")
    def discount  = column[Double]("discount")
    def quantity  = column[Int]("quantity")

    def user_fk    = foreignKey("user_fk", userId, userRepository.entities)(_.id)
    def product_fk = foreignKey("product_fk", productId, productRepository.entities)(_.id)

    def * = (id.?, userId, productId, discount, quantity) <> ((Cart.apply _).tupled, Cart.unapply)
  }

  lazy val entities: TableQuery[CartTable] = TableQuery[CartTable]
  lazy val api = new Api[CartTable](entities)
}
