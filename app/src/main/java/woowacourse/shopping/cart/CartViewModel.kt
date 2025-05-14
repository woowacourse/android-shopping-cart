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

    init {
        products += CartDatabase.cartProducts
        _cartProducts.value = products
    }

    fun deleteCartProduct(cartProduct: ProductUiModel) {
        products.remove(cartProduct)
        _cartProducts.value = products
        CartDatabase.deleteCartProduct(cartProduct)
    }
}
