package models

import models.utils.Model
import play.api.libs.json.{ Json, OFormat }

case class User(id: Option[Int], name: String, email: String, password: String, city: String, address: String) extends Model {
  override def insertId(id: Int): User = this.copy(id = Some(id))
}

object User {
  implicit val format: OFormat[User] = Json.format[User]
}
