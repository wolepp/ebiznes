package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class AuthToken(id: Option[Int], userId: Int) extends CRUDModel[AuthToken] {
  override def insertId(id: Int): AuthToken = this.copy(id = Some(id))

  override def copyWithoutId: AuthToken = this.copy(id = None)
}

object AuthToken {
  implicit val format: OFormat[AuthToken] = Json.format[AuthToken]
}
