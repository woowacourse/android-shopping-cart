package woowacourse.shopping.common.model

import java.io.Serializable

data class ProductModel(
    val picture: String,
    val title: String,
    val price: Int
) : Serializable
