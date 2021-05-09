package models

import models.utils.Model
import play.api.libs.json.{ Json, OFormat }

case class SpecialOffer(id: Option[Int], name: String, discount: Double) extends Model {
  override def insertId(id: Int): SpecialOffer = this.copy(id = Some(id))
}

object SpecialOffer {
  implicit val format: OFormat[SpecialOffer] = Json.format[SpecialOffer]
}
