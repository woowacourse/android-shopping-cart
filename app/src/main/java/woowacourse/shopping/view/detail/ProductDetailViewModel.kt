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
import java.time.LocalDateTime
import java.time.ZoneId

class ProductDetailViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _product = MutableLiveData<ProductItem>()
    val product: LiveData<ProductItem> get() = _product

    private val _lastProduct = MutableLiveData<RecentProduct>()
    val lastProduct: LiveData<RecentProduct> get() = _lastProduct

    fun loadInventoryProduct(productId: Int) {
        inventoryRepository.getOrNull(productId) { inventoryProduct ->
            if (inventoryProduct != null) {
                _product.postValue(inventoryProduct.copy(quantity = PRODUCT_MINIMUM_QUANTITY))
                updateRecentProducts(inventoryProduct)
            }
        }
    }

    fun loadLastViewedProduct() {
        recentProductRepository.getMostRecent(1) { result ->
            if (result.isNotEmpty()) _lastProduct.postValue(result.first())
        }
    }

    private fun updateRecentProducts(product: ProductItem) {
        val time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val recentProduct = RecentProduct(product.product.id, product.product.name, product.product.imageUrl, time)
        recentProductRepository.insert(recentProduct)
    }

    fun addToCart() {
        val productItem = _product.value ?: return
        shoppingCartRepository.getOrNull(productItem.product.id) { cartItem ->
            val existingQuantity = productItem.quantity
            val currentQuantity = cartItem?.quantity ?: 0
            val item = productItem.copy(quantity = existingQuantity + currentQuantity)
            inventoryRepository.insert(item)
            shoppingCartRepository.insert(item.toCartItem())
        }
    }

    fun decreaseQuantity() {
        val quantity = _product.value?.quantity ?: PRODUCT_MINIMUM_QUANTITY
        if (quantity == PRODUCT_MINIMUM_QUANTITY) return
        _product.value = _product.value?.copy(quantity = quantity - 1)
    }

    fun increaseQuantity() {
        val quantity = _product.value?.quantity ?: PRODUCT_MINIMUM_QUANTITY
        _product.value = _product.value?.copy(quantity = quantity + 1)
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
