package controllers

import models.Delivery
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.DeliveryRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class DeliveryController @Inject() (cc: ControllerComponents, deliveryRepository: DeliveryRepository)(implicit
  ec:                                   ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      deliveryRepository.api
        .list()
        .map(deliverys => {
          Ok(Json.toJson(deliverys))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      deliveryRepository.api
        .get(id)
        .map {
          case Some(delivery) => Ok(Json.toJson(delivery))
          case None           => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Delivery]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            deliveryRepository.api.create(input).map { _ =>
              Redirect(routes.DeliveryController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val deliveryResult = request.body.validate[Delivery]
        deliveryResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          deliveryData => {
            deliveryRepository.api
              .update(deliveryData.insertId(id))
              .map(delivery => {
                Ok(Json.toJson(delivery))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      deliveryRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Product $id deleted")
      }
    }
}
