package woowacourse.shopping.domain.model

import java.io.Serializable

data class Goods(
    val name: String,
    val imageUrl: String,
    val price: Int,
) : Serializable
