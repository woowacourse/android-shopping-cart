package woowacourse.shopping.model

import java.io.Serializable

data class CartProductUIModel(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String
) : Serializable
