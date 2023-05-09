package woowacourse.shopping

import java.io.Serializable

data class ProductUiModel(
    val name: String,
    val imageUrl: String,
    val price: Int
) : Serializable
