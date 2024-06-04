package woowacourse.shopping

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.ProductListItem.ShoppingProductItem.Companion.joinProductAndCart
import woowacourse.shopping.domain.RecentProductItem
import java.time.LocalDateTime

val dummyProducts =
    List(3) { id ->
        Product(
            id = id.toLong(),
            imgUrl = "",
            name = "$id",
            price = 10000,
        )
    }

val dummyProduct: Product = dummyProducts.first()

val dummyRecentProducts =
    listOf(
        RecentProductItem(
            productId = 0,
            name = "0",
            imgUrl = "",
            dateTime = LocalDateTime.of(2023, 5, 23, 11, 42),
        ),
    )

val dummyRecentProduct = dummyRecentProducts[0]

val dummyCartProducts: List<Cart> =
    List(3) {
        Cart(
            product = dummyProducts[it],
            quantity = 1,
        )
    }

val cart: Cart = dummyCartProducts.first()

val dummyShoppingProducts =
    ProductListItem.ShoppingProductItem.fromProductsAndCarts(
        dummyProducts,
        dummyCartProducts,
    )

val shoppingProduct: ProductListItem.ShoppingProductItem = joinProductAndCart(dummyProduct, cart)
