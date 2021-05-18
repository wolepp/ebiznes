package controllers

import models.WishlistItem
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.WishlistItemRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class WishlistItemController @Inject() (
  cc:                     ControllerComponents,
  wishlistItemRepository: WishlistItemRepository
)(implicit ec:            ExecutionContext)
    extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      wishlistItemRepository.api
        .list()
        .map(wishlistItems => {
          Ok(Json.toJson(wishlistItems))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      wishlistItemRepository.api
        .get(id)
        .map {
          case Some(wishlistItem) => Ok(Json.toJson(wishlistItem))
          case None               => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[WishlistItem]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            wishlistItemRepository.api.create(input).map { _ =>
              Redirect(routes.WishlistItemController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val wishlistResult = request.body.validate[WishlistItem]
        wishlistResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          wishlistItemData => {
            wishlistItemRepository.api
              .update(wishlistItemData.insertId(id))
              .map(wishlist => {
                Ok(Json.toJson(wishlist))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      wishlistItemRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"WishlistItem $id deleted")
      }
    }

}
