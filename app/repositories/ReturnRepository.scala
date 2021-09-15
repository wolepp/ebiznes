package repositories

import models.Return
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class ReturnRepository @Inject() (
  dbConfigProvider:    DatabaseConfigProvider,
  val userRepository:  UserRepository,
  val orderRepository: OrderRepository
)(implicit ec:         ExecutionContext)
    extends CRUDRepository[Return](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class ReturnTable(tag: Tag) extends BaseTable(tag) {
    def orderId = column[Int]("order_id")
    def status  = column[Int]("status")

    def orderFk = foreignKey("order_fk", orderId, orderRepository.entities)(_.id)

    def * = (id.?, orderId, status) <> ((Return.apply _).tupled, Return.unapply)
  }

  lazy val entities: TableQuery[ReturnTable] = TableQuery[ReturnTable]
  lazy val api = new Api[ReturnTable](entities)
}
