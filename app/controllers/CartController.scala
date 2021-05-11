package controllers

import models.Cart
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.CartRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class CartController @Inject() (cartRepository: CartRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      cartRepository.api
        .list()
        .map(carts => {
          Ok(Json.toJson(carts))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      cartRepository.api
        .get(id)
        .map {
          case Some(cart) => Ok(Json.toJson(cart))
          case None       => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Cart]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            cartRepository.api.create(input).map { _ =>
              Redirect(routes.CartController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val cartResult = request.body.validate[Cart]
        cartResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          cartData => {
            cartRepository.api
              .update(cartData.insertId(id))
              .map(product => {
                Ok(Json.toJson(product))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      cartRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Product $id deleted")
      }
    }
}
