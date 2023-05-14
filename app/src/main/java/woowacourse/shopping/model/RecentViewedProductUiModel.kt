package woowacourse.shopping.model

import java.io.Serializable

data class RecentViewedProductUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
) : Serializable
