package repositories

import models.CartItem
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class CartItemRepository @Inject() (
  dbConfigProvider:      DatabaseConfigProvider,
  val userRepository:    UserRepository,
  val productRepository: ProductRepository
)(implicit ec:           ExecutionContext)
    extends CRUDRepository[CartItem](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class CartTable(tag: Tag) extends BaseTable(tag) {
    def userId    = column[Int]("user_id")
    def productId = column[Int]("product_id")
    def quantity  = column[Int]("quantity")

    def user_fk    = foreignKey("user_fk", userId, userRepository.entities)(_.id)
    def product_fk = foreignKey("product_fk", productId, productRepository.entities)(_.id)

    def * = (id.?, userId, productId, quantity) <> ((CartItem.apply _).tupled, CartItem.unapply)
  }

  lazy val entities: TableQuery[CartTable] = TableQuery[CartTable]
  lazy val api = new Api[CartTable](entities)
}
