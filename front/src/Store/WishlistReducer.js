import CartReducer from "./CartReducer";

export default class WishlistReducer {

  static createWishlist(wishlists, name) {
    if (wishlists[name]) {
      return;
    }

    wishlists[name] = [];
  }

  static removeWishlist(wishlists, name) {
    if (!wishlists[name]) {
      return;
    }

    delete wishlists[name];
  }

  static addToWishlist(wishlists, wishlistName, product) {
    const wishlist = wishlists[wishlistName];

    if (wishlist) {
      if (wishlist.find(p => p.id === product.id)) {
        return;
      }
      wishlist.push(product);
    }
  }

  static removeFromWishlist(wishlists, wishlistName, product) {
    const wishlist = wishlists[wishlistName];

    if (wishlist) {
      const index = wishlist.indexOf(wishlist.find(p => p.id === product.id));
      if (index !== -1) {
        wishlist.splice(index, 1);
      }
    }
  }

  static addAllToCart(state, wishlistName) {
    const { wishlists } = state;
    const wishlist = wishlists[wishlistName];

    if (wishlist) {
      wishlist.forEach(p => {
        CartReducer.addToCartIfNotExists(state, p);
      })
    }

  }

}