package repositories

import models.Order
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class OrderRepository @Inject() (
  dbConfigProvider:           DatabaseConfigProvider,
  val userRepository:         UserRepository,
  val deliveryRepository:     DeliveryRepository,
  val specialOfferRepository: SpecialOfferRepository,
  val paymentRepository:      PaymentRepository
)(implicit ec:                ExecutionContext)
    extends CRUDRepository[Order](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class OrderTable(tag: Tag) extends BaseTable(tag) {
    def userId         = column[Int]("user_id")
    def deliveryId     = column[Option[Int]]("delivery_id")
    def specialOfferId = column[Option[Int]]("special_offer_id")
    def paymentId      = column[Option[Int]]("payment_id")
    def sum            = column[Double]("sum")

    def user_fk         = foreignKey("user_fk", userId, userRepository.entities)(_.id)
    def delivery_fk     = foreignKey("delivery_fk", deliveryId, deliveryRepository.entities)(_.id)
    def specialOffer_fk = foreignKey("special_offer_fk", specialOfferId, specialOfferRepository.entities)(_.id)
    def payment_fk      = foreignKey("payment_fk", paymentId, paymentRepository.entities)(_.id)

    def * = (id.?, userId, deliveryId, specialOfferId, paymentId, sum) <> ((Order.apply _).tupled, Order.unapply)
  }

  lazy val entities: TableQuery[OrderTable] = TableQuery[OrderTable]
  lazy val api = new Api[OrderTable](entities)
}
