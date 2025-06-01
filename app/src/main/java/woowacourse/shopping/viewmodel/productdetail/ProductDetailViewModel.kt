package woowacourse.shopping.viewmodel.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem
import woowacourse.shopping.repository.ShoppingCartRepository
import woowacourse.shopping.repository.ShoppingCartRepositoryImpl

class ProductDetailViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private val _shoppingCartItem = MutableLiveData<ShoppingCartItem>()
    val shoppingCartItem: LiveData<ShoppingCartItem> get() = _shoppingCartItem

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

    fun addToCart() {
        val cart = _shoppingCartItem.value ?: return
        shoppingCartRepository.addProduct2(cart)
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T =
                    ProductDetailViewModel(
                        ShoppingCartRepositoryImpl.getInstance(),
                    ) as T
            }
    }
}
