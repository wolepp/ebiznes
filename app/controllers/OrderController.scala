package controllers

import models.Order
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.OrderRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class OrderController @Inject() (cc: ControllerComponents, orderRepository: OrderRepository)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      orderRepository.api
        .list()
        .map(orders => {
          Ok(Json.toJson(orders))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      orderRepository.api
        .get(id)
        .map {
          case Some(order) => Ok(Json.toJson(order))
          case None        => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Order]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            orderRepository.api.create(input).map { _ =>
              Redirect(routes.OrderController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val orderResult = request.body.validate[Order]
        orderResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          orderData => {
            orderRepository.api
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
      orderRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Order $id deleted")
      }
    }
}
