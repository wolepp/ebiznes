package controllers

import models.OrderItem
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.OrderItemRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class OrderItemController @Inject() (cc: ControllerComponents, orderItemRepository: OrderItemRepository)(implicit
  ec:                                    ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      orderItemRepository.api
        .list()
        .map(orders => {
          Ok(Json.toJson(orders))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      orderItemRepository.api
        .get(id)
        .map {
          case Some(order) => Ok(Json.toJson(order))
          case None        => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[OrderItem]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            orderItemRepository.api.create(input).map { _ =>
              Redirect(routes.OrderController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val orderItemResult = request.body.validate[OrderItem]
        orderItemResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          orderData => {
            orderItemRepository.api
              .update(orderData.insertId(id))
              .map(order => {
                Ok(Json.toJson(order))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      orderItemRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Order item $id deleted")
      }
    }
}
