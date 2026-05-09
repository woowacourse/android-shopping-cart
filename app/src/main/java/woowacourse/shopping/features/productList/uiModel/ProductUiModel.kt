package woowacourse.shopping.features.productList.uiModel

data class ProductUiModel(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int,
    val isExistProductToCart: Boolean,
)
