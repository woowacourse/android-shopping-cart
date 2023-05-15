package woowacourse.shopping.cart.contract.presenter

import com.example.domain.model.CartRepository
import woowacourse.shopping.cart.CartActivity.Companion.KEY_OFFSET
import woowacourse.shopping.cart.CartItem
import woowacourse.shopping.cart.contract.CartContract
import woowacourse.shopping.mapper.toUIModel
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel

class Offset(offset: Int, private val repository: CartRepository) {
    private var offset: Int = offset
        set(value) {
            field = when {
                value < 0 -> 0
                value > repository.getAll().size -> repository.getAll().size
                else -> value
            }
        }

    fun plus(value: Int): Offset = Offset(offset + value, repository)
    fun minus(value: Int): Offset = Offset(offset - value, repository)
    fun getOffset(): Int = offset
}

class CartPresenter(
    val view: CartContract.View,
    private val repository: CartRepository,
    offset: Int = 0,
) : CartContract.Presenter {
    private var offset = Offset(offset, repository)

    init {
        setUpCarts()
    }

    override fun setUpCarts() {
        view.setCarts(
            repository.getSubList(offset.getOffset(), STEP).map { CartItem(it.toUIModel()) },
            CartUIModel(
                offset.getOffset() + STEP < repository.getAll().size,
                0 < offset.getOffset(),
                offset.getOffset() / STEP + 1,
            ),
        )
    }

    override fun pageUp() {
        offset = offset.plus(STEP)
        setUpCarts()
    }

    override fun pageDown() {
        offset = offset.minus(STEP)
        setUpCarts()
    }

    override fun removeItem(id: Int) {
        repository.remove(id)
        if (offset.getOffset() == repository.getAll().size) {
            offset = offset.minus(STEP)
        }
        setUpCarts()
    }

    override fun navigateToItemDetail(product: ProductUIModel) {
        view.navigateToItemDetail(product)
    }

    override fun saveOffsetState(outState: MutableMap<String, Int>) {
        outState[KEY_OFFSET] = offset.getOffset()
    }

    override fun restoreOffsetState(state: Map<String, Int>) {
        val savedOffset = state[KEY_OFFSET] ?: 0
        offset = Offset(savedOffset, repository)
    }

    companion object {
        private const val STEP = 5
    }
}
