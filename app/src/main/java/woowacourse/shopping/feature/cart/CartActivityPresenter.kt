package woowacourse.shopping.feature.cart

import com.example.domain.CartProduct
import woowacourse.shopping.data.datasource.cartdatasource.CartLocalDataSourceImpl
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toUi

class CartActivityPresenter(
    private val view: CartActivityContract.View,
    private val db: CartLocalDataSourceImpl,
) : CartActivityContract.Presenter {
    private var page = 1
    private val selectedItem: MutableList<CartProductItem> = mutableListOf()

    override fun loadInitialData() {
        val items = getItems(page)
        view.setPage(page)
        view.setUpAdapterData(items)
        view.updateButtonsEnabledState(page, getMaxPage())
    }

    override fun removeItem(item: CartProductItem) {
        selectedItem.remove(item)
        setBottomView()

        db.deleteColumn(item.toDomain())
        val items = getItems(page)
        val selectedState = getSelectedStateEachPage()
        view.updateAdapterData(items, selectedState)
    }

    override fun selectAllItems(isChecked: Boolean) {
        val items = getItems(page)
        if (isChecked) {
            selectedItem.addAll(items)
        } else {
            selectedItem.removeAll(items)
        }
        val selectedState = getSelectedStateEachPage()
        view.updateAdapterData(items, selectedState)

        setBottomView()
    }

    private fun getItems(page: Int): List<CartProductItem> {
        return db.getCartProducts(
            limit = ITEM_COUNT_EACH_PAGE,
            offset = (page - 1) * ITEM_COUNT_EACH_PAGE,
        ).map(CartProduct::toUi)
    }

    private fun getSelectedStateEachPage(): List<Boolean> {
        val items = getItems(page)
        val result = mutableListOf<Boolean>()

        items.forEach { cartProductItem ->
            result.add(selectedItem.contains(cartProductItem))
        }
        return result
    }

    override fun setUpButton() {
        val maxPage = getMaxPage()
        view.setButtonClickListener(maxPage)
    }

    private fun isAllSelected(): Boolean {
        val items = getItems(page)
        return selectedItem.containsAll(items)
    }

    private fun getMaxPage(): Int {
        val entireData = db.getAll()
        if (entireData.isEmpty()) return 1
        return (entireData.size - 1) / ITEM_COUNT_EACH_PAGE + 1
    }

    override fun onNextPage() {
        val maxPage = getMaxPage()
        if (page < maxPage) {
            ++page
        }
        updateData()
    }

    override fun onPreviousPage() {
        if (page > MIN_PAGE) {
            --page
        }
        updateData()
    }

    private fun updateData() {
        val items = getItems(page)
        view.setPage(page)
        val selected = getSelectedStateEachPage()
        view.updateAdapterData(items, selected)
        view.updateButtonsEnabledState(page, getMaxPage())
        view.setAllSelected(isAllSelected())
    }

    override fun updateItem(item: CartProductItem, isPlus: Boolean) {
        var newCartProduct: CartProduct = item.toDomain()

        if (cannotMinus(isPlus, item.count)) return

        if (!selectedItem.contains(item)) {
            if (isPlus) {
                newCartProduct = newCartProduct.updateCount(item.count + 1)
            } else {
                newCartProduct = newCartProduct.updateCount(item.count - 1)
            }
            updateDbAndView(newCartProduct)
            return
        }

        selectedItem.remove(item)

        if (isPlus) {
            newCartProduct = newCartProduct.updateCount(item.count + 1)
        } else {
            newCartProduct = newCartProduct.updateCount(item.count - 1)
        }
        selectedItem.add(newCartProduct.toUi())
        updateDbAndView(newCartProduct)
        setBottomView()
    }

    private fun updateDbAndView(item: CartProduct) {
        db.updateColumn(item)
        val items = getItems(page)
        val selected = getSelectedStateEachPage()
        view.updateAdapterData(items, selected)
    }

    private fun cannotMinus(isPlus: Boolean, oldCount: Int): Boolean {
        return !isPlus && oldCount <= 1
    }

    override fun toggleItemChecked(item: CartProductItem) {
        if (selectedItem.contains(item)) {
            selectedItem.remove(item)
        } else {
            selectedItem.add(item)
        }
        setBottomView()
    }

    override fun setBottomView() {
        val totalPrice = getSelectedItemTotalPrice()
        view.setPrice(totalPrice)
        view.setAllSelected(isAllSelected())
        view.setOrderNumber(selectedItem.size)
    }

    private fun getSelectedItemTotalPrice(): Int {
        return selectedItem.fold(0) { total, product ->
            total + (product.price * product.count)
        }
    }

    companion object {
        private const val ITEM_COUNT_EACH_PAGE = 5
        const val MIN_PAGE = 1
    }
}
