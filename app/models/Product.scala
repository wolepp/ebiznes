package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class Product(id: Option[Int], categoryId: Int, name: String, description: String) extends CRUDModel[Product] {
  override def insertId(id: Int): Product = this.copy(id = Some(id))

  override def copyWithoutId: Product = this.copy(id = None)
}

object Product {
  implicit val format: OFormat[Product] = Json.format[Product]
}
