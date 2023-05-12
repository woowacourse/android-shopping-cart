package woowacourse.shopping.feature.cart

import com.example.domain.CartProduct
import woowacourse.shopping.data.cart.CartDbHandler
import woowacourse.shopping.feature.list.item.CartProductListItem
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toItem

class CartActivityPresenter(
    private val view: CartActivityContract.View,
    private val db: CartDbHandler,
) : CartActivityContract.Presenter {
    private lateinit var cartItems: List<CartProductListItem>

    override fun setUpData() {
        cartItems = db.getCartProducts().map(CartProduct::toItem)
        view.setUpRecyclerView(cartItems)
    }

    override fun setUpButton() {
        val maxPageNumber = getMaxPageNumber()
        view.setBeforeButtonListener(maxPageNumber)
        view.setAfterButtonListener(maxPageNumber)
    }

    override fun updateDataEachPage(page: Int) {
        val items = getItemsEachPage(page)
        view.updateAdapterData(items)
    }

    override fun deleteData(item: CartProductListItem) {
        db.deleteColumn(item.toDomain())
        val items = db.getCartProducts().map(CartProduct::toItem)
        view.updateAdapterData(items)
    }

    private fun getMaxPageNumber(): Int {
        if (cartItems.isEmpty()) return 1
        return (cartItems.size - 1) / ITEM_COUNT_EACH_PAGE + 1
    }

    private fun getItemsEachPage(page: Int): List<CartProductListItem> {
        val onePageItemNumberStart =
            page * ITEM_COUNT_EACH_PAGE - ITEM_COUNT_EACH_PAGE
        val onePageItemNumberEnd = page * ITEM_COUNT_EACH_PAGE

        return cartItems.filterIndexed { index, _ ->
            index in onePageItemNumberStart until onePageItemNumberEnd
        }
    }

    companion object {
        private const val ITEM_COUNT_EACH_PAGE = 5
    }
}
