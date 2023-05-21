package woowacourse.shopping.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.repository.CartRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.mapper.toPresentation
import woowacourse.shopping.model.CartProductUiModel
import woowacourse.shopping.model.PageUiModel
import woowacourse.shopping.model.ProductUiModel

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
) : CartContract.Presenter {

    override var page: PageUiModel = PageUiModel(cartRepository.getAll().size, 1)
        private set

    private var _totalPrice: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice: LiveData<Int> get() = _totalPrice

    private var _totalCount: MutableLiveData<Int> = MutableLiveData(0)
    val totalCount: LiveData<Int> get() = _totalCount

    private var _allSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    val allSelected: LiveData<Boolean> get() = _allSelected

    override fun setup() {
        _allSelected.value = isAllSelected()
        updateTotalSelectedValues()
        changePageState(getCurrentPageItems())
    }

    override fun deleteCartProduct(product: ProductUiModel) {
        cartRepository.deleteProduct(product.toDomain())
        this.page = this.page.copy(allSize = this.page.allSize - 1)

        var loadedItems = getCurrentPageItems()
        if (loadedItems.isEmpty() && this.page.currentPage != 1) {
            this.page = this.page.previousPage()
            loadedItems = getCurrentPageItems()
        }

        changePageState(loadedItems)
        updateTotalSelectedValues()
        _allSelected.value = isAllSelected()
    }

    override fun loadPreviousPage() {
        this.page = this.page.previousPage()

        changePageState(getCurrentPageItems())
    }

    override fun loadNextPage() {
        this.page = this.page.nextPage()

        changePageState(getCurrentPageItems())
    }

    override fun setPage(page: PageUiModel) {
        this.page = page
        changePageState(getCurrentPageItems())
    }

    override fun increaseCartProduct(product: ProductUiModel, previousCount: Int) {
        cartRepository.addProduct(product.toDomain(), previousCount + 1)
        updateTotalSelectedValues()
    }

    override fun decreaseCartProduct(product: ProductUiModel, previousCount: Int) {
        if (previousCount == 1) {
            deleteCartProduct(product)
        } else {
            cartRepository.addProduct(product.toDomain(), previousCount - 1)
        }
        updateTotalSelectedValues()
    }

    override fun toggleCartProduct(cartProduct: CartProductUiModel, isSelected: Boolean) {
        cartRepository.updateSelection(cartProduct.productUiModel.toDomain(), isSelected)

        if (!isSelected) _allSelected.value = false
        else _allSelected.value = isAllSelected()
        updateTotalSelectedValues()
    }

    override fun toggleAllProductOnPage(isSelected: Boolean) {
        val notSelectedItems = getCurrentPageItems().filter { !it.isSelected }
        if (isSelected) {
            selectAll(notSelectedItems)
            return
        }
        if (notSelectedItems.isEmpty()) {
            deselectAll()
        }
    }

    private fun selectAll(notSelectedItems: List<CartProductUiModel>) {
        notSelectedItems.forEach { cartProduct ->
            cartRepository.updateSelection(cartProduct.productUiModel.toDomain(), true)
        }
        updateTotalSelectedValues()
        changePageState(getCurrentPageItems())
    }

    private fun deselectAll() {
        getCurrentPageItems().forEach { cartProduct ->
            cartRepository.updateSelection(cartProduct.productUiModel.toDomain(), false)
        }
        updateTotalSelectedValues()
        changePageState(getCurrentPageItems())
    }

    private fun updateTotalSelectedValues() {
        val totalItems = cartRepository.getAll().filter { it.isSelected }

        _totalPrice.value = totalItems.sumOf { it.toPresentation().totalPrice() }
        _totalCount.value = totalItems.size
    }

    private fun isAllSelected(): Boolean = getCurrentPageItems().all { it.isSelected }

    private fun getCurrentPageItems(): List<CartProductUiModel> {
        val cartProducts =
            cartRepository.getProductsByPage(page.currentPage, PageUiModel.PAGE_LOAD_SIZE)

        return cartProducts.map { it.toPresentation() }
    }

    private fun changePageState(itemModels: List<CartProductUiModel>) {
        _allSelected.value = isAllSelected()
        view.changeCartProducts(itemModels)
        view.setPageState(
            this.page.hasPreviousPage(),
            this.page.hasNextPage(),
            this.page.currentPage
        )
    }
}
