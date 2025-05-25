package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem

class ProductDetailViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _productItem = MutableLiveData<ProductItem>()
    val productItem: LiveData<ProductItem> get() = _productItem

    private val _lastProduct = MutableLiveData<RecentProduct>()
    val lastProduct: LiveData<RecentProduct> get() = _lastProduct

    fun loadInventoryProduct(productId: Int) {
        inventoryRepository.getOrNull(productId) { product ->
            if (product != null) {
                _productItem.postValue(product.copy(quantity = PRODUCT_MINIMUM_QUANTITY))
            }
        }
    }

    fun loadRecentProduct(productId: Int) {
        recentProductRepository.getLastProductBefore(productId) { recentProduct ->
            _lastProduct.postValue(recentProduct)
        }
    }

    fun addToCart() {
        val productItem = _productItem.value ?: return
        shoppingCartRepository.getOrNull(productItem.product.id) { cartItem ->
            val existingQuantity = productItem.quantity
            val currentQuantity = cartItem?.quantity ?: 0
            val item = productItem.copy(quantity = existingQuantity + currentQuantity)
            inventoryRepository.insert(item)
            shoppingCartRepository.insert(item.toCartItem())
        }
    }

    fun decreaseQuantity() {
        val quantity = _productItem.value?.quantity ?: PRODUCT_MINIMUM_QUANTITY
        if (quantity == PRODUCT_MINIMUM_QUANTITY) return
        _productItem.value = _productItem.value?.copy(quantity = quantity - 1)
    }

    fun increaseQuantity() {
        val quantity = _productItem.value?.quantity ?: PRODUCT_MINIMUM_QUANTITY
        _productItem.value = _productItem.value?.copy(quantity = quantity + 1)
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
