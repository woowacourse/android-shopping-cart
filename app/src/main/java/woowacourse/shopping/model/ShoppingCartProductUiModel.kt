package woowacourse.shopping.model

data class ShoppingCartProductUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val count: Int,
)
