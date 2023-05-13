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

    override fun setUpData(page: Int) {
        val items = getItems(page)
        view.setUpRecyclerView(items)
    }

    override fun deleteData(page: Int, item: CartProductItem) {
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
        val maxPage = getMaxPageNumber()
        view.setButtonListener(maxPage)
    }

    private fun getMaxPageNumber(): Int {
        if (cartItems.isEmpty()) return 1
        return (cartItems.size - 1) / ITEM_COUNT_EACH_PAGE + 1
    }

    companion object {
        private const val ITEM_COUNT_EACH_PAGE = 5
    }
}
