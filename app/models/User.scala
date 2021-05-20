package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class User(id: Option[Int], name: String, email: String, password: String, city: String, address: String)
    extends CRUDModel[User] {
  override def insertId(id: Int): User = this.copy(id = Some(id))

  override def copyWithoutId: User = this.copy(id = None)
}

object User {
  implicit val format: OFormat[User] = Json.format[User]
}
