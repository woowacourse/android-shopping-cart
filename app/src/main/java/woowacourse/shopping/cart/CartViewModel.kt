package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.product.catalog.ProductUiModel

class CartViewModel : ViewModel() {
    private val products = mutableListOf<ProductUiModel>()

    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    val page = MutableLiveData<Int>(INITIAL_PAGE)

    init {
        loadCartProducts()
    }

    fun deleteCartProduct(cartProduct: ProductUiModel) {
        products.remove(cartProduct)
        _cartProducts.value = products
        CartDatabase.deleteCartProduct(cartProduct)
    }

    fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        products += CartDatabase.getCartProducts(page.value ?: 0, pageSize)
        _cartProducts.value = products
        increasePage()
    }

    fun increasePage() {
        page.value = page.value?.plus(1)
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0
    }
}
