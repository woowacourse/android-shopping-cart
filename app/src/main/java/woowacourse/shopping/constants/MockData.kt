package woowacourse.shopping.constants

import woowacourse.shopping.data.remote.model.ProductResponse

object MockData {
    private const val IMAGE_BASE_URL =
        "https://github.com/CommitTheKermit/android-shopping-cart/blob/step1/images/product_image"
    private const val IMAGE_URL_SUFFIX = ".png?raw=true"

    val MOCK_PRODUCTS_LIST: List<ProductResponse> = (1..35).map { i ->
        ProductResponse(
            id = i,
            name = "품목$i",
            price = (i * 1_000),
            imageUrl = "$IMAGE_BASE_URL${(i - 1) % 5}$IMAGE_URL_SUFFIX",
        )
    }

    fun getProductResponse(id: String): ProductResponse? {
        val targetId = id.toIntOrNull() ?: return null
        val findProduct = MOCK_PRODUCTS_LIST.find { it.id == targetId } ?: return null
        return findProduct
    }
}
