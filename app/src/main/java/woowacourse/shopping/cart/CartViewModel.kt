package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.CartProductDataSource
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel(
    private val dataSource: CartProductDataSource,
) : ViewModel() {
    private val products = mutableListOf<ProductUiModel>()
    private val allCartProducts get() = dataSource.cartProducts()

    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private val _page = MutableLiveData<Int>(INITIAL_PAGE)
    val page: LiveData<Int> = _page

    init {
        products += allCartProducts
        loadCartProducts()
    }

    fun deleteCartProduct(cartProduct: ProductUiModel) {
        products.remove(cartProduct)
//        _cartProducts.value = products
        dataSource.deleteCartProduct(cartProduct)

        loadCartProducts()
    }

    fun onClick(dir: PageDirection) {
        val currentPage = page.value ?: INITIAL_PAGE
        val lastPage = (allCartProducts.size - 1) / PAGE_SIZE

        when (dir) {
            PageDirection.PREV ->
                if (currentPage > 0) {
                    decreasePage()
                    loadCartProducts()
                }

            PageDirection.NEXT ->
                if (currentPage < lastPage) {
                    increasePage()
                    loadCartProducts()
                }
        }
    }

    fun isNextButtonEnabled(): Boolean {
        val currentPage = page.value ?: INITIAL_PAGE
        val lastPage = (allCartProducts.size - 1) / PAGE_SIZE

        _isNextButtonEnabled.value = currentPage < lastPage

        return _isNextButtonEnabled.value == true
    }

    fun isPrevButtonEnabled(): Boolean {
        val currentPage = page.value ?: INITIAL_PAGE
        _isPrevButtonEnabled.value = currentPage >= 1
        return isPrevButtonEnabled.value == true
    }

    private fun increasePage() {
        _page.value = page.value?.plus(1)
    }

    private fun decreasePage() {
        _page.value = page.value?.minus(1)
    }

    private fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        var currentPage = page.value ?: INITIAL_PAGE
        val totalSize = allCartProducts.size

        while (currentPage > 0 && currentPage * pageSize >= totalSize) {
            currentPage--
        }

        _page.value = -1
        _page.value = currentPage

        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, totalSize)

        _cartProducts.value = allCartProducts.subList(startIndex, endIndex)

        _isNextButtonEnabled.value = currentPage < (totalSize - 1) / pageSize
        _isPrevButtonEnabled.value = currentPage > 0
    }

    fun eventHandler(): CartEventHandler =
        object : CartEventHandler {
            override fun onDeleteProduct(product: ProductUiModel) {
                deleteCartProduct(product)
            }

            override fun onNextPage() {
                onClick(PageDirection.NEXT)
            }

            override fun onPrevPage() {
                onClick(PageDirection.PREV)
            }
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
