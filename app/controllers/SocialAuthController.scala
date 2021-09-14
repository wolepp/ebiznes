package controllers

import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.impl.providers._
import models.User
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc.{ Action, AnyContent, Cookie, Request }
import play.filters.csrf.CSRF.Token
import play.filters.csrf.{ CSRF, CSRFAddToken }
import slick.jdbc.JdbcProfile

import java.net.URLEncoder
import javax.inject.Inject
import scala.concurrent.{ ExecutionContext, Future }

class SocialAuthController @Inject() (
  scc:              DefaultSilhouetteControllerComponents,
  addToken:         CSRFAddToken,
  dbConfigProvider: DatabaseConfigProvider
)(implicit
  ex: ExecutionContext
) extends SilhouetteController(scc) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  def authenticate(provider: String): Action[AnyContent] =
    addToken(
      Action.async { implicit request: Request[AnyContent] =>
        (
          socialProviderRegistry.get[SocialProvider](provider) match {
            case Some(p: SocialProvider with CommonSocialProfileBuilder) =>
              p.authenticate()
                .flatMap {
                  case Left(result) =>
                    Future.successful(result)
                  case Right(authInfo) =>
                    for {
                      profile <- p.retrieveProfile(authInfo)
                      futureUser <- userRepository
                                      .retrieve(profile.loginInfo)
                                      .map {
                                        case Some(user) =>
                                          Future.successful(user)
                                        case None =>
                                          userRepository.api
                                            .create(
                                              User(
                                                id = None,
                                                name = profile.fullName.getOrElse(""),
                                                email = profile.email.getOrElse(""),
                                                password = "",
                                                providerId = profile.loginInfo.providerID,
                                                providerKey = profile.loginInfo.providerKey,
                                                city = "",
                                                address = ""
                                              )
                                            )
                                      }
                      user <- futureUser
                      _    <- authInfoRepository.save(profile.loginInfo, authInfo)
                      authenticator <- authenticatorService
                                         .create(profile.loginInfo)
                      value <- authenticatorService.init(authenticator)
                      result <- authenticatorService.embed(
                                  value,
                                  Redirect("https://wlepich-ebiznes.azurewebsites.net/")
                                )
                    } yield {
                      val Token(name, value) = CSRF.getToken.get
                      result.withCookies(
                        Cookie(name, value, httpOnly = false),
                        Cookie("profile", user.email, httpOnly = false),
                        Cookie("username", URLEncoder.encode(user.name, "UTF-8"), httpOnly = false)
                      )
                    }
                }
            case _ =>
              Future.failed(
                new ProviderException(
                  s"Cannot authenticate with unexpected social provider $provider"
                )
              )
          }
        ).recover {
          case e: ProviderException =>
            logger.error("Unexpected provider error", e)
            Forbidden(Json.obj("error" -> "Forbidden"))
        }
      }
    )

}
