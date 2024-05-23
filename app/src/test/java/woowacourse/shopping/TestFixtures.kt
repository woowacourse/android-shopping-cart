package woowacourse.shopping

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductListItem
import woowacourse.shopping.domain.RecentProductItem
import java.time.LocalDateTime

val dummyProducts =
    List(51) { id ->
        Product(
            id = id.toLong(),
            imgUrl = "https://image.utoimage.com/preview/cp872722/2022/12/202212008462_500.jpg",
            name = "$id",
            price = 10000,
        )
    }

val product: Product = dummyProducts.first()

val dummyRecentProducts =
    listOf(
        RecentProductItem(
            productId = 0,
            name = "0",
            imgUrl = "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
            dateTime = LocalDateTime.now(),
        ),
    )

val dummyCartProducts: List<Cart> =
    List(3) {
        Cart(
            product = product,
            count = 1,
        )
    }

val dummyShoppingProducts =
    ProductListItem.ShoppingProductItem.fromProductsAndCarts(
        dummyProducts,
        dummyCartProducts,
    )
