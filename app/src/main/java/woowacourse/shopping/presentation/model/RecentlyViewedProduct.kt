package woowacourse.shopping.presentation.model

import woowacourse.shopping.domain.model.Product
import java.time.LocalDateTime

data class RecentlyViewedProduct(
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val product: Product,
) {
    val id: Long get() = product.id
    val name: String get() = product.name
    val itemImage: String get() = product.itemImage
    val price: Int get() = product.price
}
