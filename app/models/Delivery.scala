package models

import models.utils.{ CRUDModel, TimestampFormat }
import play.api.libs.json.{ Json, OFormat }

import java.sql.Timestamp
import java.time.Instant

case class Delivery(
  id:             Option[Int],
  status:         Int,
  shippingMethod: Int,
  deliveryDate:   String
) extends CRUDModel[Delivery] {
  override def insertId(id: Int): Delivery = this.copy(id = Some(id))

  override def copyWithoutId: Delivery = this.copy(id = None)
}

object Delivery extends TimestampFormat {
  implicit val format: OFormat[Delivery] = Json.format[Delivery]
}
