package controllers

import play.api.mvc._

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  val forms = List(
    FormInfo("Carts", routes.CartController.formList()),
    FormInfo("Categories", routes.CategoryController.formList()),
    FormInfo("Deliveries", routes.DeliveryController.formList()),
    FormInfo("Orders", routes.OrderController.formList()),
    FormInfo("Order items", routes.OrderItemController.formList()),
    FormInfo("Payments", routes.PaymentController.formList()),
    FormInfo("Products", routes.ProductController.formList()),
    FormInfo("Returns", routes.ReturnController.formList()),
    FormInfo("Special offers", routes.SpecialOfferController.formList()),
    FormInfo("Users", routes.UserController.formList()),
    FormInfo("Wishlists", routes.WishlistController.formList()),
    FormInfo("Wishlist items", routes.WishlistItemController.formList())
  )

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index: Action[AnyContent] =
    Action {
      Ok(views.html.index("Best shop", forms))
    }

}

case class FormInfo(name: String, url: Call)
