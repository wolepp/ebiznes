package models

import models.utils.Model
import play.api.libs.json.{ Json, OFormat }

case class OrderItem(id: Option[Int], orderId: Int, productId: Int) extends Model {
  override def insertId(id: Int): OrderItem = this.copy(id = Some(id))
}

object OrderItem {
  implicit val format: OFormat[OrderItem] = Json.format[OrderItem]
}
