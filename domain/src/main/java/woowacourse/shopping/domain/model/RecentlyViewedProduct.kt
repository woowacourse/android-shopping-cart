package woowacourse.shopping.domain.model

import java.time.LocalDateTime

data class RecentlyViewedProduct(
    val viewedDateTime: LocalDateTime,
    val product: Product,
) {
    val id: Long get() = product.id
    val name: String get() = product.name
    val itemImage: String get() = product.itemImage
    val price: Int get() = product.price
}
