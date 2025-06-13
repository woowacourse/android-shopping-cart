package woowacourse.shopping.viewmodel.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem
import woowacourse.shopping.repository.ShoppingCartRepository

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _shoppingCartItem = MutableLiveData<ShoppingCartItem>()
    val shoppingCartItem: LiveData<ShoppingCartItem> get() = _shoppingCartItem

    fun addToCart() {}

    fun loadProduct(product: Product) {
        _shoppingCartItem.value = ShoppingCartItem(product, 1)
    }

    fun increaseQuantity() {
        val currentShoppingCartItem = _shoppingCartItem.value ?: return
        val currentQuantity = _shoppingCartItem.value?.quantity ?: 1

        _shoppingCartItem.value = currentShoppingCartItem.copy(quantity = currentQuantity + 1)
    }

    fun decreaseQuantity() {
        val currentShoppingCartItem = _shoppingCartItem.value ?: return
        val current = _shoppingCartItem.value?.quantity ?: 1
        if (current > 1) {
            _shoppingCartItem.value = currentShoppingCartItem.copy(quantity = current - 1)
        }
    }
}
