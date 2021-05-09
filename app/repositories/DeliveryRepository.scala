package repositories

import models.Delivery
import play.api.db.slick.DatabaseConfigProvider

import java.sql.Timestamp
import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class DeliveryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends CRUDRepository[Delivery](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class DeliveryTable(tag: Tag) extends ModelTable(tag) {
    def status         = column[Int]("status")
    def shippingMethod = column[Int]("shipping_method")
    def deliveryDate   = column[Timestamp]("delivery_date")

    def * = (id.?, status, shippingMethod, deliveryDate) <> ((Delivery.apply _).tupled, Delivery.unapply)
  }

  lazy val entities: TableQuery[DeliveryTable] = TableQuery[DeliveryTable]
  lazy val api = new Api[DeliveryTable](entities)
}
