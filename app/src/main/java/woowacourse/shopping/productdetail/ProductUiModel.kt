package woowacourse.shopping.productdetail

import java.io.Serializable

data class ProductUiModel(
    val name: String,
    val imageUrl: String,
    val price: Int
) : Serializable
