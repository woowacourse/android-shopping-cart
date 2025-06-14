package woowacourse.shopping.model.products

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val price: Int,
) : Serializable
