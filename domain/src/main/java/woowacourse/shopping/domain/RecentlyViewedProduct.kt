package woowacourse.shopping.domain

import java.time.LocalDateTime
import kotlin.properties.Delegates

class RecentlyViewedProduct(
    val product: Product,
    val viewedTime: LocalDateTime
) {
    var id: Long? by Delegates.vetoable(null) { _, old, new ->
        old == null && new != null
    }

    override fun equals(other: Any?): Boolean =
        if (other is RecentlyViewedProduct) id == other.id else false

    override fun hashCode(): Int = id.hashCode()
}