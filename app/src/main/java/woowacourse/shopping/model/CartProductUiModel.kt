package woowacourse.shopping.model

data class CartProductUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val count: Int,
    val selected: Boolean
)
