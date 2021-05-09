package models

import models.utils.Model
import play.api.libs.json.{ Json, OFormat }

case class Wishlist(id: Option[Int], userId: Int, name: String) extends Model {
  override def insertId(id: Int): Wishlist = this.copy(id = Some(id))
}

object Wishlist {
  implicit val format: OFormat[Wishlist] = Json.format[Wishlist]
}
