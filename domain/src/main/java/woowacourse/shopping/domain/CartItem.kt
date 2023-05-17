package woowacourse.shopping.domain

import java.time.LocalDateTime
import kotlin.properties.Delegates

class CartItem(
    val product: Product,
    val addedTime: LocalDateTime,
    val count: Int
) {
    var id: Long? by Delegates.vetoable(null) { _, old, new ->
        old == null && new != null
    }

    override fun equals(other: Any?): Boolean = if (other is CartItem) id == other.id else false

    override fun hashCode(): Int = id.hashCode()
}