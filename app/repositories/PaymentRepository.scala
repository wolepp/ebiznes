package repositories

import models.Payment
import play.api.db.slick.DatabaseConfigProvider

import java.sql.Timestamp
import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class PaymentRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends CRUDRepository[Payment](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class PaymentTable(tag: Tag) extends BaseTable(tag) {
    def status    = column[Int]("status")
    def updatedAt = column[Timestamp]("updated_at")

    def * = (id.?, status, updatedAt) <> ((Payment.apply _).tupled, Payment.unapply)
  }

  lazy val entities: TableQuery[PaymentTable] = TableQuery[PaymentTable]
  lazy val api = new Api[PaymentTable](entities)
}
