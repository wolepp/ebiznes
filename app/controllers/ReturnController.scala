package controllers

import models.Return
import play.api.mvc._
import repositories.{ CRUDRepository, ReturnRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class ReturnController @Inject() (
  cc:               MessagesControllerComponents,
  returnRepository: ReturnRepository
)(implicit ec:      ExecutionContext)
    extends CRUDController[Return](cc) {
  override val indexRedirect: Call                   = routes.ReturnController.index()
  override val repository:    CRUDRepository[Return] = returnRepository
}
