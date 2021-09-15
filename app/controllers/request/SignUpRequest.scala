package controllers.request

import play.api.libs.json.{ Json, OFormat }

case class SignUpRequest(
  email:    String,
  password: String,
  name:     Option[String] = None,
  address:  Option[String] = None,
  city:     Option[String] = None
)

object SignUpRequest {
  implicit val signUpRequestForm: OFormat[SignUpRequest] = Json.format[SignUpRequest]
}
