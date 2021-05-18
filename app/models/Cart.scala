package models

import models.utils.CRUDModel
import play.api.libs.json.{ Json, OFormat }

case class Cart(id: Option[Int], userId: Int, productId: Int, discount: Double, quantity: Int) extends CRUDModel[Cart] {
  override def insertId(id: Int): Cart = this.copy(id = Some(id))
}

object Cart {
  implicit val format: OFormat[Cart] = Json.format[Cart]
}
