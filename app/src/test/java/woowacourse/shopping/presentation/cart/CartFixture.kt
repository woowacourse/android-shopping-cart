package woowacourse.shopping.presentation.cart

import woowacourse.shopping.R
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi

object CartFixture {
    @OptIn(ExperimentalUuidApi::class)
    val cartItems =
        listOf(
            CartItem(
                product =
                    Product(
                        imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                        productName = "PET보틀-정사각형(370ml)1",
                        price = Price(10000),
                    ),
                count = 1,
            ),
            CartItem(
                product =
                    Product(
                        imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image2}",
                        productName = "PET보틀-밀크티(500ml)",
                        price = Price(12000),
                    ),
                count = 1,
            ),
            CartItem(
                product =
                    Product(
                        imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image3}",
                        productName = "PET보틀-정사각형(500ml)",
                        price = Price(10000),
                    ),
                count = 1,
            ),
            CartItem(
                product =
                    Product(
                        imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image4}",
                        productName = "PET보틀-납작(200ml)",
                        price = Price(12000),
                    ),
                count = 1,
            ),
            CartItem(
                product =
                    Product(
                        imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image2}",
                        productName = "PET보틀-밀크티(500ml)",
                        price = Price(12000),
                    ),
                count = 1,
            ),
            CartItem(
                product =
                    Product(
                        imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image6}",
                        productName = "PET보틀-단지(200ml)",
                        price = Price(12000),
                    ),
                count = 1,
            ),
            CartItem(
                product =
                    Product(
                        imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                        productName = "[든든] 동원 스위트콘",
                        price = Price(99800),
                    ),
                count = 1,
            ),
        )
}
