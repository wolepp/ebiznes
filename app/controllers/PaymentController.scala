package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class PaymentController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] =
    Action {
      Ok("List of payments")
    }

  def get(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Payment $id")
    }

  def create(): Action[AnyContent] =
    Action {
      Ok("Payment created")
    }

  def update(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Payment $id updated")
    }

  def delete(id: Long): Action[AnyContent] =
    Action {
      Ok(s"Payment $id removed")
    }
}
