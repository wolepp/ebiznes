package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class OrderItem(id: Option[Int], orderId: Int, productId: Int) extends CRUDModel[OrderItem] {
  override def insertId(id: Int): OrderItem = this.copy(id = Some(id))
}

object OrderItem {
  implicit val format: OFormat[OrderItem] = Json.format[OrderItem]
}
