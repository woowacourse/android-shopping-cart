package woowacourse.shopping.mock

import woowacourse.shopping.domain.cart.CartItem
import woowacourse.shopping.domain.product.ImageUrl
import woowacourse.shopping.domain.product.Price
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductName

object MockData {
    val productInfo =
        Product(
            name = ProductName("케로로"),
            imageUrl =
                ImageUrl(
                    "https://img1.daumcdn.net/thumb/R1280x0.fwebp/?fname=http://t1.daumcdn.net/brunch/service/user/cnoC/image/81kyXbEZD1IOwgNjto1sFm7PPfI",
                ),
            price = Price(10000),
        )

    val products =
        List(100) { index ->
            Product(
                name = ProductName("케로로 ${index + 1}"),
                imageUrl =
                    ImageUrl(
                        "https://img1.daumcdn.net/thumb/R1280x0.fwebp/?fname=http://t1.daumcdn.net/brunch/service/user/cnoC/image/81kyXbEZD1IOwgNjto1sFm7PPfI",
                    ),
                price = Price(10000),
                id = index.toString(),
            )
        }

    val cartItems =
        List(11) { index ->
            CartItem(
                product =
                    Product(
                        id = index.toString(),
                        name = ProductName("케로로 ${index + 1}"),
                        imageUrl = ImageUrl("https://picsum.photos/seed/product$index/200/200"),
                        price = Price((index + 1) * 10000),
                    ),
            )
        }
}
