package woowacourse.shopping.model.product

import java.io.Serializable

data class Product(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val price: Int,
) : Serializable
