package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.toCartItem
import woowacourse.shopping.data.toUiModel
import woowacourse.shopping.domain.RecentItem
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel
import java.time.LocalDateTime
import java.time.ZoneId

class ProductDetailViewModel(
    private val inventoryRepository: InventoryRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> get() = _product

    private val _lastProduct = MutableLiveData<RecentItem>()
    val lastProduct: LiveData<RecentItem> get() = _lastProduct

    fun loadInventoryProduct(productId: Int) {
        inventoryRepository.getOrNull(productId) { inventoryProduct ->
            if (inventoryProduct != null) {
                val item = inventoryProduct.toUiModel().copy(quantity = PRODUCT_MINIMUM_QUANTITY)
                _product.postValue(item)
                updateRecentProducts(item)
            }
        }
    }

    fun loadLastViewedProduct() {
        recentProductRepository.getMostRecent(1) { result ->
            if (result.isNotEmpty()) _lastProduct.postValue(result.first())
        }
    }

    private fun updateRecentProducts(product: ProductUiModel) {
        val time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val recentProduct = RecentItem(product.product.id, product.product.name, product.product.imageUrl, time)
        recentProductRepository.insert(recentProduct)
    }

    fun addToCart() {
        val productItem = _product.value ?: return
        shoppingCartRepository.getOrNull(productItem.product.id) { cartItem ->
            val existingQuantity = productItem.quantity
            val currentQuantity = cartItem?.quantity ?: 0
            val item = productItem.copy(quantity = existingQuantity + currentQuantity)
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
