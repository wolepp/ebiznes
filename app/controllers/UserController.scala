package controllers

import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.twirl.api.Html
import repositories.{ CRUDRepository, UserRepository }

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class UserController @Inject() (
  cc:             MessagesControllerComponents,
  userRepository: UserRepository
)(implicit ec:    ExecutionContext)
    extends CRUDController[User](cc) {

  val indexRedirect: Call                 = routes.UserController.index()
  val repository:    CRUDRepository[User] = userRepository

  val form: Form[User] = Form {
    mapping(
      "id"       -> optional(number),
      "name"     -> nonEmptyText,
      "email"    -> email,
      "password" -> nonEmptyText,
      "city"     -> nonEmptyText,
      "address"  -> nonEmptyText
    )(User.apply)(User.unapply)
  }

  val formListRedirect: Call = routes.UserController.formList()

  def listView(entities: Seq[User]): Html = views.html.user_list(entities)

  def createView(form: Form[User])(implicit request: MessagesRequest[_]): Html = views.html.user_create(form)

  def updateView(id: Int, form: Form[User])(implicit request: MessagesRequest[_]): Html =
    views.html.user_update(id, form)
}
