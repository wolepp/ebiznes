package repositories

import models.WishlistItem
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class WishlistItemRepository @Inject() (
  dbConfigProvider:       DatabaseConfigProvider,
  val wishlistRepository: WishlistRepository,
  val productRepository:  ProductRepository
)(implicit ec:            ExecutionContext)
    extends CRUDRepository[WishlistItem](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class WishlistItemTable(tag: Tag) extends ModelTable(tag) {
    def wishlistId = column[Int]("wishlist_id")
    def productId  = column[Int]("product_id")

    def wishlist_fk = foreignKey("wishlist_fk", wishlistId, wishlistRepository.entities)(_.id)
    def product_fk  = foreignKey("product_fk", productId, productRepository.entities)(_.id)

    def * = (id.?, wishlistId, productId) <> ((WishlistItem.apply _).tupled, WishlistItem.unapply)
  }

  lazy val entities: TableQuery[WishlistItemTable] = TableQuery[WishlistItemTable]
  lazy val api = new Api[WishlistItemTable](entities)
}
