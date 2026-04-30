package woowacourse.shopping.constants

import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product

object MockData {
    private const val IMAGE_BASE_URL =
        "https://github.com/CommitTheKermit/android-shopping-cart/blob/step1/images/product_image"
    private const val IMAGE_URL_SUFFIX = ".png?raw=true"

    val MOCK_PRODUCTS: List<Product> = (1..40).map { i ->
        Product(
            name = "품목$i",
            price = Money(i * 1_000),
            imageUrl = "$IMAGE_BASE_URL${(i - 1) % 5}$IMAGE_URL_SUFFIX",
        )
    }
}
