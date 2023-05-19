package woowacourse.shopping.domain

import java.time.LocalDateTime
import kotlin.properties.Delegates

class CartItem(
    val product: Product,
    val addedTime: LocalDateTime,
    count: Int
) {
    var id: Long? by Delegates.vetoable(null) { _, old, new ->
        old == null && new != null
    }
    var count: Int = count
        private set

    fun plusCount() {
        count++
    }

    fun minusCount() {
        if (count > 1) count--
    }

    override fun equals(other: Any?): Boolean = if (other is CartItem) id == other.id else false

    override fun hashCode(): Int = id.hashCode()
}