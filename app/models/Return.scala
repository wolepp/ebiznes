package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class Return(id: Option[Int], userId: Int, orderId: Int, status: Int) extends CRUDModel[Return] {
  override def insertId(id: Int): Return = this.copy(id = Some(id))
}

object Return {
  implicit val format: OFormat[Return] = Json.format[Return]
}
