package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel : ViewModel() {
    private val products = mutableListOf<ProductUiModel>()
    private val allCartProducts = CartDatabase.cartProducts

    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    val page = MutableLiveData<Int>(INITIAL_PAGE)

    init {
        products += allCartProducts
        loadCartProducts()
    }

    fun deleteCartProduct(cartProduct: ProductUiModel) {
        products.remove(cartProduct)
        _cartProducts.value = products
        CartDatabase.deleteCartProduct(cartProduct)
    }

    fun onClick(dir: Int) {
        if (dir == NEXT_BUTTON) increasePage() else decreasePage()
        loadCartProducts()
    }

    private fun increasePage() {
        page.value = page.value?.plus(1)
    }

    private fun decreasePage() {
        page.value = page.value?.minus(1)
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
        private const val PREV_BUTTON = 1
        private const val NEXT_BUTTON = 2
    }
}
