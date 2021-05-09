package models

import models.utils.Model
import play.api.libs.json.{ Json, OFormat }

case class Return(id: Option[Int], userId: Int, orderId: Int, status: Int) extends Model {
  override def insertId(id: Int): Return = this.copy(id = Some(id))
}

object Return {
  implicit val format: OFormat[Return] = Json.format[Return]
}
