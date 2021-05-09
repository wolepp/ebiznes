package models.utils

trait Model {
  val id: Option[Int]
  def insertId(id: Int): Model
}
