package woowacourse.shopping.feature.cart

import com.example.domain.CartProduct
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.feature.list.item.CartProductItem
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toItem

class CartActivityPresenter(
    private val view: CartActivityContract.View,
    private val db: CartDbHandler,
) : CartActivityContract.Presenter {
    private val cartItems: List<CartProductItem> = db.getAll().map(CartProduct::toItem)
    private var page = 1

    override fun setUpData() {
        val items = getItems(page)
        view.setPage(page)
        view.setUpRecyclerView(items)
        view.updateButtonsEnabledState(page, getMaxPage())
    }

    override fun updateData() {
        val items = getItems(page)
        view.setPage(page)
        view.updateAdapterData(items)
        view.updateButtonsEnabledState(page, getMaxPage())
    }

    override fun deleteData(item: CartProductItem) {
        db.deleteColumn(item.toDomain())
        val items = getItems(page)
        view.updateAdapterData(items)
    }

    private fun getItems(page: Int): List<CartProductItem> {
        return db.getCartProducts(
            limit = ITEM_COUNT_EACH_PAGE,
            offset = (page - 1) * ITEM_COUNT_EACH_PAGE,
        ).map(CartProduct::toItem)
    }

    override fun setUpButton() {
        val maxPage = getMaxPage()
        view.setButtonListener(maxPage)
    }

    private fun getMaxPage(): Int {
        if (cartItems.isEmpty()) return 1
        return (cartItems.size - 1) / ITEM_COUNT_EACH_PAGE + 1
    }

    override fun nextPage() {
        val maxPage = getMaxPage()
        if (page < maxPage) {
            ++page
        }
        updateData()
    }

    override fun previousPage() {
        if (page > MIN_PAGE) {
            --page
        }
        updateData()
    }

    companion object {
        private const val ITEM_COUNT_EACH_PAGE = 5
        const val MIN_PAGE = 1
    }
}
