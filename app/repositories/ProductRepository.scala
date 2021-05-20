package repositories

import models.Product
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class ProductRepository @Inject() (
  dbConfigProvider:       DatabaseConfigProvider,
  val categoryRepository: CategoryRepository
)(implicit ec:            ExecutionContext)
    extends CRUDRepository[Product](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class ProductTable(tag: Tag) extends BaseTable(tag) {
    def categoryId  = column[Int]("category_id")
    def name        = column[String]("name")
    def description = column[String]("description")

    def category_fk = foreignKey("category_fk", categoryId, categoryRepository.entities)(_.id)

    def * = (id.?, categoryId, name, description) <> ((Product.apply _).tupled, Product.unapply)
  }

  lazy val entities: TableQuery[ProductTable] = TableQuery[ProductTable]
  lazy val api = new Api[ProductTable](entities)
}
