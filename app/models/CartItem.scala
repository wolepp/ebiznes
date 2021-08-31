package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class CartItem(id: Option[Int], userId: Int, productId: Int, quantity: Int) extends CRUDModel[CartItem] {
  override def insertId(id: Int): CartItem = this.copy(id = Some(id))

  override def copyWithoutId: CartItem = this.copy(id = None)
}

object CartItem {
  implicit val format: OFormat[CartItem] = Json.format[CartItem]
}
