package controllers

import models.SpecialOffer
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.SpecialOfferRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class SpecialOfferController @Inject() (
  cc:                     ControllerComponents,
  specialOfferRepository: SpecialOfferRepository
)(implicit ec:            ExecutionContext)
    extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      specialOfferRepository.api
        .list()
        .map(specialOffers => {
          Ok(Json.toJson(specialOffers))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      specialOfferRepository.api
        .get(id)
        .map {
          case Some(specialOffer) => Ok(Json.toJson(specialOffer))
          case None               => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[SpecialOffer]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            specialOfferRepository.api.create(input).map { _ =>
              Redirect(routes.SpecialOfferController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val specialOfferResult = request.body.validate[SpecialOffer]
        specialOfferResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          specialOfferData => {
            specialOfferRepository.api
              .update(specialOfferData.insertId(id))
              .map(specialOffer => {
                Ok(Json.toJson(specialOffer))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      specialOfferRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"SpecialOffer $id deleted")
      }
    }
}
