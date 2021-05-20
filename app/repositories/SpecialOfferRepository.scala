package repositories

import models.SpecialOffer
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class SpecialOfferRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends CRUDRepository[SpecialOffer](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class SpecialOfferTable(tag: Tag) extends BaseTable(tag) {
    def name     = column[String]("name")
    def discount = column[Int]("discount")

    def * = (id.?, name, discount) <> ((SpecialOffer.apply _).tupled, SpecialOffer.unapply)
  }

  lazy val entities: TableQuery[SpecialOfferTable] = TableQuery[SpecialOfferTable]
  lazy val api = new Api[SpecialOfferTable](entities)
}
