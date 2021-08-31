package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class Wishlist(id: Option[Int], userId: Int, name: Option[String]) extends CRUDModel[Wishlist] {
  override def insertId(id: Int): Wishlist = this.copy(id = Some(id))

  override def copyWithoutId: Wishlist = this.copy(id = None)
}

object Wishlist {
  implicit val format: OFormat[Wishlist] = Json.format[Wishlist]
}
