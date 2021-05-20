package models.utils

trait CRUDModel[M] {
  val id: Option[Int]
  def insertId(id: Int): M
  def copyWithoutId: M
}
