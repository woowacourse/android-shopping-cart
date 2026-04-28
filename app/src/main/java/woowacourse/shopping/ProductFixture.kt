package woowacourse.shopping

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import kotlin.uuid.ExperimentalUuidApi

object ProductFixture {
    @OptIn(ExperimentalUuidApi::class)
    val productList =
        listOf(
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                productName = "PET보틀-정사각형(370ml)",
                price = Price(10000),
            ),
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image2}",
                productName = "PET보틀-밀크티(500ml)",
                price = Price(12000),
            ),
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image3}",
                productName = "PET보틀-정사각형(500ml)",
                price = Price(10000),
            ),
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image4}",
                productName = "PET보틀-납작(200ml)",
                price = Price(12000),
            ),
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image5}",
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image6}",
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
        )
}
