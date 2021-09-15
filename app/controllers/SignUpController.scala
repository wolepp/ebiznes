package controllers

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.impl.providers._
import controllers.request.SignUpRequest
import models.User
import play.api.libs.json.Json
import play.api.mvc.{ Action, AnyContent, Cookie, Request }
import play.filters.csrf.CSRF.Token
import play.filters.csrf.{ CSRF, CSRFAddToken }

import javax.inject.Inject
import scala.concurrent.{ ExecutionContext, Future }
import scala.language.postfixOps

class SignUpController @Inject() (components: DefaultSilhouetteControllerComponents, addToken: CSRFAddToken)(implicit
  ex:                                         ExecutionContext
) extends AbstractAuthController(components) {

  def signUp: Action[AnyContent] =
    addToken(
      unsecuredAction.async { implicit request: Request[AnyContent] =>
        val json          = request.body.asJson.get
        val signUpRequest = json.as[SignUpRequest]
        val loginInfo     = LoginInfo(CredentialsProvider.ID, signUpRequest.email)

        userRepository.retrieve(loginInfo).flatMap {
          case Some(_) =>
            Future.successful(Forbidden(Json.obj("error" -> "User already has an account")))
          case None =>
            val authInfo           = passwordHasherRegistry.current.hash(signUpRequest.password)
            val Token(name, value) = CSRF.getToken.get
            userRepository.api
              .create(
                User(
                  id = None,
                  name = signUpRequest.name.getOrElse(""),
                  email = signUpRequest.email,
                  password = authInfo.password,
                  providerId = CredentialsProvider.ID,
                  providerKey = signUpRequest.email,
                  city = signUpRequest.city.getOrElse(""),
                  address = signUpRequest.address.getOrElse("")
                )
              )
              .flatMap { user =>
                authInfoRepository
                  .add(loginInfo, authInfo)
                  .map(_ => user)
              }
              .flatMap { user =>
                authenticateUser(user).map(_ => user)
              }
              .map { user =>
                Json.toJson(user.withoutPassword)
              }
              .map { json =>
                Created(json)
                  .withCookies(Cookie(name, value, httpOnly = false))
              }
        }
      }
    )
}
