package repositories

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import models.User
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends CRUDRepository[User](dbConfigProvider)
    with IdentityService[User] {

  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends BaseTable(tag) {
    def name        = column[String]("name")
    def email       = column[String]("email")
    def password    = column[String]("password")
    def providerId  = column[String]("provider_id")
    def providerKey = column[String]("provider_key")
    def city        = column[String]("city")
    def address     = column[String]("address")

    def * = (id.?, name, email, password, providerId, providerKey, city, address) <> ((User.apply _).tupled, User.unapply)
  }

  lazy val entities: TableQuery[UserTable] = TableQuery[UserTable]
  lazy val api = new Api[UserTable](entities)

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] =
    db.run {
      entities
        .filter(u => {
          u.providerId === loginInfo.providerID &&
            u.providerKey === loginInfo.providerKey
        })
        .result
        .headOption
    }
}
