package models

import models.utils.Model
import play.api.libs.json.{ Json, OFormat }

case class Product(id: Option[Int], categoryId: Int, name: String, description: String) extends Model {
  override def insertId(id: Int): Product = this.copy(id = Some(id))
}

object Product {
  implicit val format: OFormat[Product] = Json.format[Product]
}
