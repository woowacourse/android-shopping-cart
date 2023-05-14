package woowacourse.shopping.model

import java.io.Serializable

data class ShoppingCartProductUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val count: Int
) : Serializable
