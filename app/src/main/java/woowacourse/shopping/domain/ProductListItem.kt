package woowacourse.shopping.domain

import java.time.LocalDateTime

sealed class ProductListItem {
    data class ShoppingProductItem(
        val id: Long,
        val name: String,
        val imgUrl: String,
        val price: Long,
    ) : ProductListItem()

    data class RecentProductItems(val items: List<RecentProductItem>) : ProductListItem()
}

data class RecentProductItem(
    val productId: Long,
    val name: String,
    val imgUrl: String,
    val dateTime: LocalDateTime,
)
