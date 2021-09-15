package models

import com.mohiva.play.silhouette.api.{ Identity, LoginInfo }
import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class User(
  id:          Option[Int],
  name:        String,
  email:       String,
  password:    String,
  providerId:  String,
  providerKey: String,
  city:        String,
  address:     String
) extends CRUDModel[User]
    with Identity {
  override def insertId(id: Int): User = this.copy(id = Some(id))

  override def copyWithoutId: User = this.copy(id = None)

  def withoutPassword: User = this.copy(password = "")
}

object User {
  implicit val loginInfoFormat: OFormat[LoginInfo] = Json.format[LoginInfo]
  implicit val format:          OFormat[User]      = Json.format[User]
}
