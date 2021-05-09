package repositories

import models.User
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends CRUDRepository[User](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends ModelTable(tag) {
    def name     = column[String]("name")
    def email    = column[String]("email")
    def password = column[String]("password")
    def city     = column[String]("city")
    def address  = column[String]("address")

    def * = (id.?, name, email, password, city, address) <> ((User.apply _).tupled, User.unapply)
  }

  lazy val entities: TableQuery[UserTable] = TableQuery[UserTable]
  lazy val api = new Api[UserTable](entities)
}
