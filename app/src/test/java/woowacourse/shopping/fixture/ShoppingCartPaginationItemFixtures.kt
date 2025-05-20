package woowacourse.shopping.fixture

import woowacourse.shopping.view.product.ProductsItem.LoadItem
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem

val SHOPPING_CART_PRODUCT_ITEMS_5_MORE =
    getProducts(5).map { ShoppingCartItem.ProductItem(it) } + LoadItem
val SHOPPING_CART_PRODUCT_ITEMS_4 = getProducts(4).map { ShoppingCartItem.ProductItem(it) }
