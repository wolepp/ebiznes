package models

import models.utils.{ CRUDModel, TimestampFormat }
import play.api.libs.json.{ Json, OFormat }

import java.sql.Timestamp
import java.time.Instant

case class Payment(id: Option[Int], status: Int, updatedAt: Timestamp = Timestamp.from(Instant.now))
    extends CRUDModel[Payment] {
  override def insertId(id: Int): Payment = this.copy(id = Some(id))

  override def copyWithoutId: Payment = this.copy(id = None)
}

object Payment extends TimestampFormat {
  implicit val format: OFormat[Payment] = Json.format[Payment]
}
