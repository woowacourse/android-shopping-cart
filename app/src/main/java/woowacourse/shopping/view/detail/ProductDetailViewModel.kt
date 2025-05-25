package woowacourse.shopping.view.detail

import android.util.Log
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
    private val _inventoryProduct = MutableLiveData<InventoryProduct>()
    val inventoryProduct: LiveData<InventoryProduct> get() {
        return _inventoryProduct
    }

    private val _lastProduct = MutableLiveData<RecentProduct>()
    val lastProduct: LiveData<RecentProduct> get() {
        Log.wtf("asdf", "${_lastProduct.value}")
        return _lastProduct
    }

    fun loadInventoryProduct(productId: Int) {
        inventoryRepository.getOrNull(productId) { product ->
            if (product != null) {
                _inventoryProduct.postValue(product.copy(quantity = PRODUCT_MINIMUM_QUANTITY))
            }
        }
    }

    fun loadRecentProduct(productId: Int) {
        recentProductRepository.getLastProductBefore(productId) { recentProduct ->
            _lastProduct.postValue(recentProduct)
        }
    }

    fun addToCart(product: InventoryProduct) {
        shoppingCartRepository.getOrNull(product.id) { cartItem ->
            val currentQuantity = cartItem?.quantity ?: 0
            val item = product.copy(quantity = currentQuantity + (_inventoryProduct.value?.quantity ?: 0))
            inventoryRepository.insert(item)
            shoppingCartRepository.insert(item.toCartItem())
        }
    }

    fun decreaseQuantity() {
        val quantity = _inventoryProduct.value?.quantity ?: PRODUCT_MINIMUM_QUANTITY
        if (quantity == PRODUCT_MINIMUM_QUANTITY) return
        _inventoryProduct.value = _inventoryProduct.value?.copy(quantity = quantity - 1)
    }

    fun increaseQuantity() {
        val quantity = _inventoryProduct.value?.quantity ?: PRODUCT_MINIMUM_QUANTITY
        _inventoryProduct.value = _inventoryProduct.value?.copy(quantity = quantity + 1)
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
