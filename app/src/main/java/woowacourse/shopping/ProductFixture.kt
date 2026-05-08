package woowacourse.shopping

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object ProductFixture {
    @OptIn(ExperimentalUuidApi::class)
    private val productIds =
        listOf(
            Uuid.parse("00000000-0000-0000-0000-000000000001"),
            Uuid.parse("00000000-0000-0000-0000-000000000002"),
            Uuid.parse("00000000-0000-0000-0000-000000000003"),
            Uuid.parse("00000000-0000-0000-0000-000000000004"),
            Uuid.parse("00000000-0000-0000-0000-000000000005"),
            Uuid.parse("00000000-0000-0000-0000-000000000006"),
            Uuid.parse("00000000-0000-0000-0000-000000000007"),
            Uuid.parse("00000000-0000-0000-0000-000000000008"),
            Uuid.parse("00000000-0000-0000-0000-000000000009"),
            Uuid.parse("00000000-0000-0000-0000-00000000000a"),
            Uuid.parse("00000000-0000-0000-0000-00000000000b"),
            Uuid.parse("00000000-0000-0000-0000-00000000000c"),
            Uuid.parse("00000000-0000-0000-0000-00000000000d"),
            Uuid.parse("00000000-0000-0000-0000-00000000000e"),
            Uuid.parse("00000000-0000-0000-0000-00000000000f"),
            Uuid.parse("00000000-0000-0000-0000-000000000010"),
            Uuid.parse("00000000-0000-0000-0000-000000000011"),
            Uuid.parse("00000000-0000-0000-0000-000000000012"),
            Uuid.parse("00000000-0000-0000-0000-000000000013"),
            Uuid.parse("00000000-0000-0000-0000-000000000014"),
            Uuid.parse("00000000-0000-0000-0000-000000000015"),
            Uuid.parse("00000000-0000-0000-0000-000000000016"),
            Uuid.parse("00000000-0000-0000-0000-000000000017"),
            Uuid.parse("00000000-0000-0000-0000-000000000018"),
        )

    private fun imageResourceUri(
        packageName: String,
        drawableResId: Int,
    ): String = "android.resource://$packageName/$drawableResId"

    @OptIn(ExperimentalUuidApi::class)
    fun productList(packageName: String): List<Product> =
        listOf(
            Product(
                productId = productIds[0],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image1),
                productName = "PET보틀-정사각형(370ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[1],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image2),
                productName = "PET보틀-밀크티(500ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[2],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image3),
                productName = "PET보틀-정사각형(500ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[3],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image4),
                productName = "PET보틀-납작(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[4],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image5),
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[5],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image6),
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[6],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image7),
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
            Product(
                productId = productIds[7],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image8),
                productName = "PET보틀-정사각형(370ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[8],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image9),
                productName = "PET보틀-밀크티(500ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[9],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image10),
                productName = "PET보틀-정사각형(500ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[10],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image11),
                productName = "PET보틀-납작(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[11],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image12),
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[12],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image13),
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[13],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image14),
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
            Product(
                productId = productIds[14],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image15),
                productName = "PET보틀-정사각형(370ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[15],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image16),
                productName = "PET보틀-밀크티(500ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[16],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image17),
                productName = "PET보틀-정사각형(500ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[17],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image18),
                productName = "PET보틀-납작(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[18],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image19),
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[19],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image20),
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[20],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image21),
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
            Product(
                productId = productIds[21],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image22),
                productName = "PET보틀-단지(400ml)",
                price = Price(10000),
            ),
            Product(
                productId = productIds[22],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image23),
                productName = "PET보틀-단지(200ml)",
                price = Price(12000),
            ),
            Product(
                productId = productIds[23],
                imageUrl = imageResourceUri(packageName, R.drawable.product_image24),
                productName = "[든든] 동원 스위트콘",
                price = Price(99800),
            ),
        )
}
