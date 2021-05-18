package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class Category(id: Option[Int], name: String, parentCategoryId: Option[Int]) extends CRUDModel[Category] {
  override def insertId(id: Int): Category = this.copy(id = Some(id))
}

object Category {
  implicit val format: OFormat[Category] = Json.format[Category]
}
