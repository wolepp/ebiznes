package models.utils

import play.api.libs.json.{ Json, Reads, Writes }

import java.sql.Timestamp

class TimestampFormat {
  implicit val timestampReads: Reads[Timestamp] = {
    implicitly[Reads[Long]].map(new Timestamp(_))
  }

  implicit val timestampWrites: Writes[Timestamp] = {
    implicitly[Writes[Long]].contramap(_.getTime)
  }
}
