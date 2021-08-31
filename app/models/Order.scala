package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class Order(
  id:         Option[Int],
  userId:     Int,
  deliveryId: Option[Int],
  paymentId:  Option[Int]
) extends CRUDModel[Order] {
  override def insertId(id: Int): Order = this.copy(id = Some(id))

  override def copyWithoutId: Order = this.copy(id = None)
}

object Order {
  implicit val format: OFormat[Order] = Json.format[Order]
}
