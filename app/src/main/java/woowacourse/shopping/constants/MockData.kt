package woowacourse.shopping.constants

import woowacourse.shopping.ui.state.ProductUiModel

object MockData {
    private const val IMAGE_BASE_URL =
        "https://github.com/CommitTheKermit/android-shopping-cart/blob/step1/images/product_image"
    private const val IMAGE_URL_SUFFIX = ".png?raw=true"

    val MOCK_PRODUCTS: List<ProductUiModel> = listOf(
        ProductUiModel(
            id = "1",
            name = "Pet보틀-정사각형 500ml",
            price = 1_200,
            imageUrl = "${IMAGE_BASE_URL}0$IMAGE_URL_SUFFIX",
        ),
        ProductUiModel(
            id = "2",
            name = "유리병-라운드 350ml",
            price = 2_500,
            imageUrl = "${IMAGE_BASE_URL}1$IMAGE_URL_SUFFIX",
        ),
        ProductUiModel(
            id = "3",
            name = "알루미늄캔-슬림 250ml",
            price = 1_800,
            imageUrl = "${IMAGE_BASE_URL}2$IMAGE_URL_SUFFIX",
        ),
        ProductUiModel(
            id = "4",
            name = "종이팩-사각 1L",
            price = 3_400,
            imageUrl = "${IMAGE_BASE_URL}3$IMAGE_URL_SUFFIX",
        ),
        ProductUiModel(
            id = "5",
            name = "파우치-스탠딩 200ml",
            price = 990,
            imageUrl = "${IMAGE_BASE_URL}4$IMAGE_URL_SUFFIX",
        ),
        ProductUiModel(
            id = "6",
            name = "Pet보틀-슬림 1.5L",
            price = 2_100,
            imageUrl = "${IMAGE_BASE_URL}0$IMAGE_URL_SUFFIX",
        ),
        ProductUiModel(
            id = "7",
            name = "유리병-각진 750ml",
            price = 5_800,
            imageUrl = "${IMAGE_BASE_URL}1$IMAGE_URL_SUFFIX",
        ),
        ProductUiModel(
            id = "8",
            name = "알루미늄캔-스탠다드 355ml",
            price = 2_300,
            imageUrl = "${IMAGE_BASE_URL}2$IMAGE_URL_SUFFIX",
        ),
    )
}
