package controllers

import models.Wishlist
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.WishlistRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class WishlistController @Inject() (cc: ControllerComponents, wishlistRepository: WishlistRepository)(implicit
  ec:                                   ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      wishlistRepository.api
        .list()
        .map(wishlists => {
          Ok(Json.toJson(wishlists))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      wishlistRepository.api
        .get(id)
        .map {
          case Some(wishlist) => Ok(Json.toJson(wishlist))
          case None           => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Wishlist]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            wishlistRepository.api.create(input).map { _ =>
              Redirect(routes.WishlistController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val wishlistResult = request.body.validate[Wishlist]
        wishlistResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          wishlistData => {
            wishlistRepository.api
              .update(wishlistData.insertId(id))
              .map(wishlist => {
                Ok(Json.toJson(wishlist))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      wishlistRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Wishlist $id deleted")
      }
    }

}
