package woowacourse.shopping.domain.product

import java.io.Serializable

data class Product(
    val id: Long,
    val name: String,
    val price: Int,
) : Serializable
