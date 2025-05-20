package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.cart.CartItem.ProductItem
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.CartProductDataSource
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel(
    private val dataSource: CartProductDataSource = CartDatabase,
) : ViewModel() {
    private val allCartProductsSize get() = dataSource.getAllProductsSize()
    private val products = mutableListOf<ProductUiModel>()

    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private val _page = MutableLiveData<Int>(INITIAL_PAGE)
    val page: LiveData<Int> = _page
    val currentPage: Int get() = page.value ?: 1

    init {
        loadCartProducts()
    }

    fun deleteCartProduct(cartProduct: ProductItem) {
        products.remove(cartProduct.productItem)
        dataSource.deleteCartProduct(cartProduct.productItem)

        val currentPage = page.value ?: INITIAL_PAGE
        val updatedSize = dataSource.getAllProductsSize()

        val startIndex = currentPage * PAGE_SIZE
        if (startIndex >= updatedSize && currentPage > 0) {
            decreasePage()
        }

        loadCartProducts()
    }

    fun onPaginationButtonClick(buttonDirection: Int) {
        val currentPage = page.value ?: INITIAL_PAGE
        val lastPage = (allCartProductsSize - 1) / PAGE_SIZE

        when (buttonDirection) {
            PREV_BUTTON ->
                if (currentPage > 0) {
                    decreasePage()
                    loadCartProducts()
                }

            NEXT_BUTTON ->
                if (currentPage < lastPage) {
                    increasePage()
                    loadCartProducts()
                }
        }
    }

    fun updateButtons() {
        checkNextButtonEnabled()
        checkPrevButtonEnabled()
    }

    private fun checkNextButtonEnabled() {
        val currentPage = page.value ?: INITIAL_PAGE
        val lastPage = (allCartProductsSize - 1) / PAGE_SIZE
        _isNextButtonEnabled.value = currentPage < lastPage
    }

    private fun checkPrevButtonEnabled() {
        val currentPage = page.value ?: INITIAL_PAGE
        _isPrevButtonEnabled.value = currentPage >= 1
    }

    private fun increasePage() {
        _page.value = page.value?.plus(1)
    }

    private fun decreasePage() {
        _page.value = page.value?.minus(1)
    }

    private fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        val currentPage = page.value ?: INITIAL_PAGE
        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, allCartProductsSize)
        if (startIndex >= allCartProductsSize) {
            _cartProducts.value = emptyList()
            return
        }
        val pagedProducts: List<ProductUiModel> = dataSource.getCartProductsInRange(startIndex, endIndex)
        _cartProducts.value = pagedProducts
        updateButtons()
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0
        private const val PREV_BUTTON = 1
        private const val NEXT_BUTTON = 2
    }
}
