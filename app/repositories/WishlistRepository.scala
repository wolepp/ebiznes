package repositories

import models.Wishlist
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class WishlistRepository @Inject() (
  dbConfigProvider:   DatabaseConfigProvider,
  val userRepository: UserRepository
)(implicit ec:        ExecutionContext)
    extends CRUDRepository[Wishlist](dbConfigProvider)
    with EntitiesProvider {

  import dbConfig._
  import profile.api._

  class WishlistTable(tag: Tag) extends BaseTable(tag) {
    def userId = column[Int]("user_id")
    def name   = column[String]("name")

    def user_fk = foreignKey("user_fk", userId, userRepository.entities)(_.id)

    def * = (id.?, userId, name) <> ((Wishlist.apply _).tupled, Wishlist.unapply)
  }

  def entities: TableQuery[WishlistTable] = TableQuery[WishlistTable]
  lazy val api = new Api[WishlistTable](entities)
}
