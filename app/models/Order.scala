package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class Order(
  id:             Option[Int],
  userId:         Int,
  deliveryId:     Option[Int],
  specialOfferId: Option[Int],
  paymentId:      Option[Int],
  sum:            Double
) extends CRUDModel[Order] {
  override def insertId(id: Int): Order = this.copy(id = Some(id))
}

object Order {
  implicit val format: OFormat[Order] = Json.format[Order]
}
