package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.CartProductDataSource
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel(
    private val dataSource: CartProductDataSource,
) : ViewModel(),
    CartEventHandler {
    private val allCartProducts get() = dataSource.cartProducts()
    private val allCartProductsSize get() = dataSource.getCartProductsSize()

    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private var currentPage: Int = INITIAL_PAGE

    private val _pageEvent = SingleLiveEvent<Int>()
    val pageEvent: LiveData<Int> = _pageEvent

    init {
        loadCartProducts()
    }

    override fun onDeleteProduct(cartProduct: ProductUiModel) {
        dataSource.deleteCartProduct(cartProduct)
        loadCartProducts()
    }

    override fun onNextPage() {
        val lastPage = (allCartProductsSize - 1) / PAGE_SIZE
        if (currentPage < lastPage) {
            increasePage()
            loadCartProducts()
        }
    }

    override fun onPrevPage() {
        if (currentPage > 0) {
            decreasePage()
            loadCartProducts()
        }
    }

    override fun isNextButtonEnabled(): Boolean {
        val lastPage = (allCartProductsSize - 1) / PAGE_SIZE
        return currentPage < lastPage
    }

    override fun isPrevButtonEnabled(): Boolean = currentPage > 0

    override fun isPaginationEnabled(): Boolean = isNextButtonEnabled() || isPrevButtonEnabled()

    override fun getPage(): Int = currentPage

    private fun increasePage() {
        currentPage += 1
        _pageEvent.value = currentPage
    }

    private fun decreasePage() {
        currentPage -= 1
        _pageEvent.value = currentPage
    }

    private fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        var current = currentPage
        val totalSize = allCartProductsSize

        while (current > 0 && current * pageSize >= totalSize) {
            current--
        }

        currentPage = current
        _pageEvent.value = current

        val startIndex = current * pageSize
        val endIndex = minOf(startIndex + pageSize, totalSize)

        _cartProducts.value = allCartProducts.subList(startIndex, endIndex)
        _isNextButtonEnabled.value = current < (totalSize - 1) / pageSize
        _isPrevButtonEnabled.value = current > 0
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0

        fun factory(dataSource: CartProductDataSource): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                        return CartViewModel(dataSource) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
