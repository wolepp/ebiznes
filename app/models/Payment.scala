package models

import models.utils.{ Model, TimestampFormat }
import play.api.libs.json.{ Json, OFormat }

import java.sql.Timestamp
import java.time.Instant

case class Payment(id: Option[Int], status: Int, updatedAt: Timestamp = Timestamp.from(Instant.now)) extends Model {
  override def insertId(id: Int): Payment = this.copy(id = Some(id))
}

object Payment extends TimestampFormat {
  implicit val format: OFormat[Payment] = Json.format[Payment]
}
