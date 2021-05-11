package controllers

import models.Payment
import play.api.libs.json.{ JsValue, Json }
import play.api.mvc._
import repositories.PaymentRepository

import javax.inject._
import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class PaymentController @Inject() (cc: ControllerComponents, paymentRepository: PaymentRepository)(implicit
  ec:                                  ExecutionContext
) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      paymentRepository.api
        .list()
        .map(payments => {
          Ok(Json.toJson(payments))
        })
    }

  def get(id: Int): Action[AnyContent] =
    Action.async {
      paymentRepository.api
        .get(id)
        .map {
          case Some(payment) => Ok(Json.toJson(payment))
          case None          => NotFound(Json.obj("error" -> "Not found"))
        }
    }

  def create(): Action[JsValue] =
    Action(parse.json).async { request =>
      request.body
        .validate[Payment]
        .fold(
          _ => {
            Future(BadRequest("Invalid json content"))
          },
          input => {
            paymentRepository.api.create(input).map { _ =>
              Redirect(routes.PaymentController.index())
            }
          }
        )
    }

  def update(id: Int): Action[JsValue] =
    Action(parse.json).async { implicit request =>
      {
        val paymentResult = request.body.validate[Payment]
        paymentResult.fold(
          _ => {
            Future.successful(BadRequest(Json.obj("error" -> "Invalid json")))
          },
          paymentData => {
            paymentRepository.api
              .update(paymentData.insertId(id))
              .map(payment => {
                Ok(Json.toJson(payment))
              })
          }
        )
      }
    }

  def delete(id: Int): Action[AnyContent] =
    Action.async {
      paymentRepository.api.delete(id).map {
        case 0 => NotFound(Json.obj("error" -> "Not found"))
        case _ => Ok(s"Payment $id deleted")
      }
    }

}
