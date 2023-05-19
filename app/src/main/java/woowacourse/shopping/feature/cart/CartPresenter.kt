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

    override fun setup() {
        _totalPrice.value = calculateTotalPrice()
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

        _totalPrice.value = calculateTotalPrice()
    }

    override fun loadPreviousPage() {
        this.page = this.page.previousPage()

        val loadedItems = getCurrentPageItems()
        changePageState(loadedItems)
    }

    override fun loadNextPage() {
        this.page = this.page.nextPage()

        val loadedItems = getCurrentPageItems()
        changePageState(loadedItems)
    }

    override fun setPage(page: PageUiModel) {
        this.page = page
        changePageState(getCurrentPageItems())
    }

    override fun increaseCartProduct(product: ProductUiModel, previousCount: Int) {
        cartRepository.addProduct(product.toDomain(), previousCount + 1)
        _totalPrice.value = calculateTotalPrice()
    }

    override fun decreaseCartProduct(product: ProductUiModel, previousCount: Int) {
        if (previousCount == 1) {
            deleteCartProduct(product)
        } else {
            cartRepository.addProduct(product.toDomain(), previousCount - 1)
        }
        _totalPrice.value = calculateTotalPrice()
    }

    override fun toggleCartProduct(cartProduct: CartProductUiModel, isSelected: Boolean) {
        cartRepository.updateSelection(cartProduct.productUiModel.toDomain(), isSelected)
        _totalPrice.value = calculateTotalPrice()
    }

    private fun calculateTotalPrice(): Int = cartRepository.getAll()
        .filter { it.isSelected }
        .sumOf { it.toPresentation().totalPrice() }

    private fun getCurrentPageItems(): List<CartProductUiModel> {
        val cartProducts =
            cartRepository.getProductsByPage(page.currentPage, PageUiModel.PAGE_LOAD_SIZE)

        return cartProducts.map { it.toPresentation() }
    }

    private fun changePageState(itemModels: List<CartProductUiModel>) {
        view.changeCartProducts(itemModels)
        view.setPageState(
            this.page.hasPreviousPage(),
            this.page.hasNextPage(),
            this.page.currentPage
        )
    }
}
