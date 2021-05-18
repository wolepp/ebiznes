package controllers

import models.Return
import play.api.mvc._
import repositories.ReturnRepository

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class ReturnController @Inject() (
  cc:              MessagesControllerComponents,
  orderRepository: ReturnRepository
)(implicit ec:     ExecutionContext)
    extends CRUDController[Return](orderRepository, routes.ReturnController.index(), cc)
