package woowacourse.shopping.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel : ViewModel() {
    private val products = mutableListOf<ProductUiModel>()
    private val allCartProducts get() = CartDatabase.cartProducts

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
        CartDatabase.deleteCartProduct(cartProduct)

        loadCartProducts()
    }

    fun onClick(dir: Int) {
        if (page.value == 0 && dir == PREV_BUTTON) {
            return
        }
        if ((allCartProducts.size / 5) == page.value && dir == NEXT_BUTTON) {
            return
        }

        if (dir == NEXT_BUTTON) increasePage() else decreasePage()
        loadCartProducts()
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
        Log.d("loadCart", "$allCartProducts, $products, $cartProducts")
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0
        private const val PREV_BUTTON = 1
        private const val NEXT_BUTTON = 2
    }
}
