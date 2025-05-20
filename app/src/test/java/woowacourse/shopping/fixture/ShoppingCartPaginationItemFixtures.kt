package woowacourse.shopping.fixture

import woowacourse.shopping.view.shoppingCart.ShoppingCartItem

val SHOPPING_CART_PRODUCT_ITEMS_5_PAGE_1MORE =
    getProducts(5).map { ShoppingCartItem.ProductItem(it) } +
        ShoppingCartItem.PaginationItem(
            1,
            true,
            false,
        )
val SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_1 =
    getProducts(4).map { ShoppingCartItem.ProductItem(it) } +
        ShoppingCartItem.PaginationItem(
            1,
            false,
            false,
        )

val SHOPPING_CART_PRODUCT_ITEMS_4_PAGE_2 =
    getProducts(4).map { ShoppingCartItem.ProductItem(it) } +
            ShoppingCartItem.PaginationItem(
                2,
                false,
                true,
            )
