package woowacourse.shopping.domain

import java.io.Serializable

data class Product(
    val name: String,
    val price: Int,
    val imageUrl: String,
) : Serializable
