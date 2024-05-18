package woowacourse.shopping

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product

val products =
    List(51) { id ->
        Product(
            id = id.toLong(),
            imgUrl = "https://image.utoimage.com/preview/cp872722/2022/12/202212008462_500.jpg",
            name = "$id",
            price = 10000,
        )
    }

val product: Product = products.first()

val dummyCarts: List<Cart> =
    List(3) {
        Cart(
            product = product,
            count = 1,
        )
    }
