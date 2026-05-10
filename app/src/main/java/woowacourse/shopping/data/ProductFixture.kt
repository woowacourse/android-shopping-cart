package woowacourse.shopping.data

import woowacourse.shopping.R
import woowacourse.shopping.domain.model.product.Price
import woowacourse.shopping.domain.model.product.Product
import kotlin.uuid.ExperimentalUuidApi

object ProductFixture {
    @OptIn(ExperimentalUuidApi::class)
    val productList =
        listOf(
            Product(
                productId = 1,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                productName = "PET보틀-정사각형(370ml)1",
                price = Price(10000),
            ),
            Product(
                productId = 2,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image2}",
                productName = "PET보틀-밀크티(500ml)",
                price = Price(12000),
            ),
            Product(
                productId = 3,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image3}",
                productName = "PET보틀-정사각형(500ml)",
                price = Price(10000),
            ),
            Product(
                productId = 4,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image4}",
                productName = "PET보틀-납작(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = 5,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image5}",
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = 6,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image6}",
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = 7,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
            Product(
                productId = 8,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                productName = "PET보틀-정사각형(370ml)2",
                price = Price(10000),
            ),
            Product(
                productId = 9,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image2}",
                productName = "PET보틀-밀크티(500ml)",
                price = Price(12000),
            ),
            Product(
                productId = 10,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image3}",
                productName = "PET보틀-정사각형(500ml)",
                price = Price(10000),
            ),
            Product(
                productId = 11,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image4}",
                productName = "PET보틀-납작(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = 12,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image5}",
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = 13,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image6}",
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = 14,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
            Product(
                productId = 15,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image1}",
                productName = "PET보틀-정사각형(370ml)3",
                price = Price(10000),
            ),
            Product(
                productId = 16,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image2}",
                productName = "PET보틀-밀크티(500ml)",
                price = Price(12000),
            ),
            Product(
                productId = 17,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image3}",
                productName = "PET보틀-정사각형(500ml)",
                price = Price(10000),
            ),
            Product(
                productId = 18,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image4}",
                productName = "PET보틀-납작(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = 19,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image5}",
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = 20,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image6}",
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = 21,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
            Product(
                productId = 22,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image5}",
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = 23,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image6}",
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = 24,
                imageUrl = "android.resource://woowacourse.shopping/${R.drawable.product_image7}",
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
        )
}
