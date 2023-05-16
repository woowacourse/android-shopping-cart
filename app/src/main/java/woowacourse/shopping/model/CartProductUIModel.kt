package woowacourse.shopping.model

import java.io.Serializable

data class CartProductUIModel(
    val id: Int,
    val name: String,
    val count: Int,
    val checked: Boolean,
    val price: Int,
    val imageUrl: String
) : Serializable
