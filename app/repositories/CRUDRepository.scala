package repositories

import models.utils.Model
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ ExecutionContext, Future }
import scala.language.reflectiveCalls
import scala.reflect.{ ClassTag, classTag }

abstract class CRUDRepository[M <: Model: ClassTag](dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends EntitiesProvider {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  abstract class ModelTable(tag: Tag) extends Table[M](tag, classTag[M].runtimeClass.getSimpleName.toLowerCase) {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  }

  class Api[T <: ModelTable](entities: TableQuery[T]) {

    def get(id: Int): Future[Option[M]] =
      db.run {
        entities.filter(_.id === id).result.headOption
      }

    def list(): Future[Seq[M]] =
      db.run {
        entities.result
      }

    def create(model: M): Future[M] = {
      db.run {
        entities
          .returning {
            entities.map(_.id)
          }
          .into { (m: M, id: Int) =>
            m.insertId(id).asInstanceOf[M]
          } += model
      }
    }

    def update(model: M): Future[Int] =
      db.run {
        entities
          .filter(_.id === model.id)
          .update(model)
      }

    def delete(id: Int): Future[Int] =
      db.run {
        entities.filter(_.id === id).delete
      }

  }
}
