package woowacourse.shopping.domain.model

import java.io.Serializable

data class Goods(
    val name: String,
    val price: Int,
    val imageRes: Int,
) : Serializable
