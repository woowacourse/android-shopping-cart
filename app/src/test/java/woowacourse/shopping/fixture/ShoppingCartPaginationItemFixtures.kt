package woowacourse.shopping.fixture

import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct
import woowacourse.shopping.view.shoppingCart.ShoppingCartItem

val SHOPPING_CART_PRODUCT_ITEMS_5_PAGE_1MORE =
    getProducts(5).map {
        ShoppingCartItem.ProductItem(
            ShoppingCartProduct(
                product = it,
                quantity = 0,
            ),
        )
    } +
        ShoppingCartItem.PaginationItem(
            1,
            true,
            false,
        )
val SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_1 =
    getProducts(4).map {
        ShoppingCartItem.ProductItem(
            ShoppingCartProduct(
                product = it,
                quantity = 0,
            ),
        )
    } +
        ShoppingCartItem.PaginationItem(
            1,
            false,
            false,
        )

val SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_2 =
    getProducts(4).map {
        ShoppingCartItem.ProductItem(
            ShoppingCartProduct(
                product = it,
                quantity = 0,
            ),
        )
    } +
        ShoppingCartItem.PaginationItem(
            2,
            false,
            true,
        )
