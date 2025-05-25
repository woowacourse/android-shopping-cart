package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct
import woowacourse.shopping.view.inventory.item.RecentProduct

class ProductDetailViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _lastProduct = MutableLiveData<RecentProduct>()
    val lastProduct: LiveData<RecentProduct> get() = _lastProduct
    private val _quantity = MutableLiveData(PRODUCT_MINIMUM_QUANTITY)
    val quantity: LiveData<Int> get() = _quantity

    fun loadRecentProduct(product: InventoryProduct) {
        recentProductRepository.getLastProductBefore(product) { recentProduct ->
            _lastProduct.postValue(recentProduct)
        }
    }

    fun addToCart(product: InventoryProduct) {
        shoppingCartRepository.getOrNull(product.id) { cartItem ->
            val currentQuantity = cartItem?.quantity ?: 0
            val item = product.copy(quantity = currentQuantity + (quantity.value ?: 0))
            inventoryRepository.insert(item)
            shoppingCartRepository.insert(item.toCartItem())
        }
    }

    fun decreaseQuantity() {
        if (_quantity.value == PRODUCT_MINIMUM_QUANTITY) return
        _quantity.value = _quantity.value?.minus(1)
    }

    fun increaseQuantity() {
        _quantity.value = _quantity.value?.plus(1)
    }

    companion object {
        private const val PRODUCT_MINIMUM_QUANTITY = 1

        @Suppress("UNCHECKED_CAST")
        fun createFactory(
            inventoryRepository: InventoryRepository,
            shoppingCartRepository: ShoppingCartRepository,
            recentProductRepository: RecentProductRepository,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return (
                        ProductDetailViewModel(
                            inventoryRepository,
                            shoppingCartRepository,
                            recentProductRepository,
                        ) as T
                    )
                }
            }
        }
    }
}
