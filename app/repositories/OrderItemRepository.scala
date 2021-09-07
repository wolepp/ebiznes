package repositories

import models.OrderItem
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class OrderItemRepository @Inject() (
  dbConfigProvider:      DatabaseConfigProvider,
  val orderRepository:   OrderRepository,
  val productRepository: ProductRepository
)(implicit ec:           ExecutionContext)
    extends CRUDRepository[OrderItem](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class OrderItemTable(tag: Tag) extends BaseTable(tag) {
    def orderId   = column[Int]("order_id")
    def productId = column[Int]("product_id")
    def quantity  = column[Int]("quantity")

    def order_fk   = foreignKey("order_fk", orderId, orderRepository.entities)(_.id)
    def product_fk = foreignKey("product_fk", productId, productRepository.entities)(_.id)

    def * = (id.?, orderId, productId, quantity) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
  }

  lazy val entities: TableQuery[OrderItemTable] = TableQuery[OrderItemTable]
  lazy val api = new Api[OrderItemTable](entities)
}
