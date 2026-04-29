package woowacourse.shopping.constants

import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product

object MockData {
    private const val IMAGE_BASE_URL =
        "https://github.com/CommitTheKermit/android-shopping-cart/blob/step1/images/product_image"
    private const val IMAGE_URL_SUFFIX = ".png?raw=true"

    val MOCK_PRODUCTS: List<Product> = listOf(
        Product(
            name = "Pet보틀-정사각형 500ml",
            price = Money(1_200),
            imageUrl = "${IMAGE_BASE_URL}0$IMAGE_URL_SUFFIX",
        ),
        Product(
            name = "유리병-라운드 350ml",
            price = Money(2_500),
            imageUrl = "${IMAGE_BASE_URL}1$IMAGE_URL_SUFFIX",
        ),
        Product(
            name = "알루미늄캔-슬림 250ml",
            price = Money(1_800),
            imageUrl = "${IMAGE_BASE_URL}2$IMAGE_URL_SUFFIX",
        ),
        Product(
            name = "종이팩-사각 1L",
            price = Money(3_400),
            imageUrl = "${IMAGE_BASE_URL}3$IMAGE_URL_SUFFIX",
        ),
        Product(
            name = "파우치-스탠딩 200ml",
            price = Money(990),
            imageUrl = "${IMAGE_BASE_URL}4$IMAGE_URL_SUFFIX",
        ),
        Product(
            name = "Pet보틀-슬림 1.5L",
            price = Money(2_100),
            imageUrl = "${IMAGE_BASE_URL}0$IMAGE_URL_SUFFIX",
        ),
        Product(
            name = "유리병-각진 750ml",
            price = Money(5_800),
            imageUrl = "${IMAGE_BASE_URL}1$IMAGE_URL_SUFFIX",
        ),
        Product(
            name = "알루미늄캔-스탠다드 355ml",
            price = Money(2_300),
            imageUrl = "${IMAGE_BASE_URL}2$IMAGE_URL_SUFFIX",
        ),
    )
}
