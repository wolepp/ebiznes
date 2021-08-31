package repositories

import models.utils.CRUDModel
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ ExecutionContext, Future }
import scala.language.reflectiveCalls
import scala.reflect.ClassTag

abstract class CRUDRepository[M <: CRUDModel[M]: ClassTag](dbConfigProvider: DatabaseConfigProvider)(implicit
  ec: ExecutionContext
) extends EntitiesProvider {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  val modelName: String = implicitly[ClassTag[M]].runtimeClass.getSimpleName

  import dbConfig._
  import profile.api._

  abstract class BaseTable(tag: Tag) extends Table[M](tag, modelName.toLowerCase) {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  }

  val api: Api[_]

  class Api[T <: BaseTable](entities: TableQuery[T]) {

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
            m.insertId(id)
          } += model
      }
    }

    def update(model: M): Future[Option[M]] = {
      db.run {
        entities
          .filter(_.id === model.id)
          .update(model)
          .map {
            case 0 => None
            case _ => Some(model)
          }
      }
    }

    def delete(idOpt: Option[Int]): Future[Option[M]] =
      idOpt match {
        case Some(id) =>
          get(id).map(entity => {
            db.run {
              entities
                .filter(_.id === id)
                .delete
                .map {
                  case 0 => None
                  case _ => Some()
                }
            }
            entity
          })
        case None => Future { None }
      }
  }
}
