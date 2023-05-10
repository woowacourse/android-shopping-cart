package woowacourse.shopping.model

import java.io.Serializable

data class ProductUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int
) : Serializable
