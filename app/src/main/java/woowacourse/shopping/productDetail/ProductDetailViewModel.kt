package woowacourse.shopping.productDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.db.ShoppingCartDatabase
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductEntity
import woowacourse.shopping.factory.BaseViewModelFactory
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.RecentlyViewedRepository
import woowacourse.shopping.service.MockWebService
import kotlin.concurrent.thread

class ProductDetailViewModel(application: Application, val productId: Int) :
    AndroidViewModel(application) {
    private val productStore = MockWebService()
    private val recentlyViewedRepository = RecentlyViewedRepository(application)

    val product: Product
        get() = getProduct()

    private fun getProduct(): Product {
        var product = Product(0, "", "", 0)
        thread {
            product = productStore.findProductById(productId)
        }.join()
        return product
    }

    private val _cartItem = MutableLiveData<CartItem>()
    val cartItem: LiveData<CartItem> get() = _cartItem

    private val _lastViewedProduct = MediatorLiveData<Product>()
    val lastViewedProduct: LiveData<Product> get() = _lastViewedProduct

    private val _shouldShowLastViewedProduct = MutableLiveData<Boolean>()
    val shouldShowLastViewedProduct: LiveData<Boolean> get() = _shouldShowLastViewedProduct

    private val recentProducts: LiveData<List<RecentlyViewedProductEntity>> =
        recentlyViewedRepository.getRecentProducts(productId)

    init {
        loadCartItem()
        addProductToRecentlyViewed()
        observeLastViewedProduct()
    }

    private fun loadCartItem() {
        viewModelScope.launch {
            val cartItems = ShoppingCartDatabase.getCartItems()
            _cartItem.value = cartItems.find { it.productId == productId } ?: CartItem(productId, 0)
        }
    }

    private fun addProductToRecentlyViewed() {
        viewModelScope.launch {
            recentlyViewedRepository.addProduct(productId)
        }
    }

    private fun observeLastViewedProduct() {
        _lastViewedProduct.addSource(recentProducts) { lastViewedEntities ->
            val lastViewedEntity = lastViewedEntities.firstOrNull()
            var lastViewedProduct = Product(0, "", "", 0)
            if (lastViewedEntity != null) {
                thread {
                    lastViewedProduct = productStore.findProductById(lastViewedEntity.productId)
                }.join()
                _lastViewedProduct.value = lastViewedProduct
                val shouldShow = lastViewedProduct.id != productId
                _shouldShowLastViewedProduct.value = shouldShow
            } else {
                _shouldShowLastViewedProduct.value = false
            }
        }
    }

    fun addProductToCart() {
        viewModelScope.launch {
            if ((_cartItem.value?.quantity ?: 0) > 0) {
                addProductCount()
            } else {
                ShoppingCartDatabase.addProductToCart(productId)
                loadCartItem()
            }
        }
    }

    private fun addProductCount() {
        viewModelScope.launch {
            ShoppingCartDatabase.addProductCount(productId)
            loadCartItem()
        }
    }

    fun subtractProductCount() {
        viewModelScope.launch {
            ShoppingCartDatabase.subtractProductCount(productId)
            loadCartItem()
        }
    }

    companion object {
        fun factory(
            application: Application,
            productId: Int,
        ): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductDetailViewModel(application, productId) }
        }
    }
}
