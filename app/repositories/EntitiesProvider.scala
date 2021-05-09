package repositories

import slick.lifted.TableQuery

trait EntitiesProvider {
  def entities: TableQuery[_]
}
