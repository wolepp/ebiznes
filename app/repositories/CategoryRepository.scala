package repositories

import models.Category
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext

@Singleton
class CategoryRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends CRUDRepository[Category](dbConfigProvider) {

  import dbConfig._
  import profile.api._

  class CategoryTable(tag: Tag) extends BaseTable(tag) {
    def name             = column[String]("name")
    def parentCategoryId = column[Option[Int]]("parent_category_id")

    def parentCategory_fk = foreignKey("parent_category_fk", parentCategoryId, entities)(_.id)

    def * = (id.?, name, parentCategoryId) <> ((Category.apply _).tupled, Category.unapply)
  }

  lazy val entities: TableQuery[CategoryTable] = TableQuery[CategoryTable]
  lazy val api = new Api[CategoryTable](entities)
}
