package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.inventory.InventoryRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.toCartProduct
import woowacourse.shopping.data.toUiModel
import woowacourse.shopping.domain.RecentItem
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel

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
        inventoryRepository.getOrNull(productId) { product ->
            if (product != null) {
                val item = product.toUiModel().copy(quantity = PRODUCT_MINIMUM_QUANTITY)
                _product.postValue(item)
                recentProductRepository.insert(product)
            }
        }
    }

    fun loadLastViewedProduct() {
        recentProductRepository.getMostRecent(1) { result ->
            if (result.isNotEmpty()) _lastProduct.postValue(result.first())
        }
    }

    fun addToCart() {
        val productItem = _product.value ?: return
        shoppingCartRepository.getOrNull(productItem.product.id) { cartItem ->
            val existingQuantity = productItem.quantity
            val currentQuantity = cartItem?.quantity ?: 0
            val item = productItem.copy(quantity = existingQuantity + currentQuantity)
            shoppingCartRepository.insert(item.toCartProduct()) {}
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
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = extras[APPLICATION_KEY] as ShoppingApplication
                    return (
                        ProductDetailViewModel(
                            application.inventoryRepository,
                            application.shoppingCartRepository,
                            application.recentProductRepository,
                        ) as T
                    )
                }
            }
    }
}
