package repositories

import models.AuthToken
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class AuthTokenRepository @Inject() (
  dbConfigProvider:   DatabaseConfigProvider,
  val userRepository: UserRepository
)(implicit ec:        ExecutionContext)
    extends CRUDRepository[AuthToken](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class AuthTokenTable(tag: Tag) extends BaseTable(tag) {
    def userId = column[Int]("user_id")

    def userFk = foreignKey("user_fk", userId, userRepository.entities)(_.id)

    def * = (id.?, userId) <> ((AuthToken.apply _).tupled, AuthToken.unapply)
  }

  lazy val entities: TableQuery[AuthTokenTable] = TableQuery[AuthTokenTable]
  lazy val api = new Api[AuthTokenTable](entities)
}
