package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.CartProductDataSource
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel(
    private val dataSource: CartProductDataSource = CartDatabase,
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
        _cartProducts.value = products
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
        val currentPage = page.value ?: INITIAL_PAGE
        val startIndex = currentPage * pageSize
        val endIndex = minOf(startIndex + pageSize, allCartProducts.size)
        if (startIndex >= allCartProducts.size) {
            _cartProducts.value = emptyList()
            return
        }
        val pagedProducts = allCartProducts.subList(startIndex, endIndex)
        _cartProducts.value = pagedProducts
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0
    }
}
