package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class WishlistItem(id: Option[Int], wishlistId: Int, productId: Int) extends CRUDModel[WishlistItem] {
  override def insertId(id: Int): WishlistItem = this.copy(id = Some(id))
}

object WishlistItem {
  implicit val format: OFormat[WishlistItem] = Json.format[WishlistItem]
}
