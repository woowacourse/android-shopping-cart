package woowacourse.shopping.ui.cart

import com.example.domain.repository.CartRepository
import woowacourse.shopping.utils.OffsetInterface

class CartOffset(offset: Int, private val repository: CartRepository) : OffsetInterface {
    private var offset: Int = offset
        set(value) {
            field = when {
                value < 0 -> 0
                value > repository.getAll().size -> repository.getAll().size
                else -> value
            }
        }

    override fun plus(value: Int): CartOffset = CartOffset(offset + value, repository)
    override fun minus(value: Int): CartOffset = CartOffset(offset - value, repository)
    override fun getOffset(): Int = offset
}
