package woowacourse.shopping.productDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.db.ShoppingCart
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductEntity
import woowacourse.shopping.factory.BaseViewModelFactory
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.DummyProductStore
import woowacourse.shopping.repository.RecentlyViewedRepository

class ProductDetailViewModel(application: Application, val productId: Int) :
    AndroidViewModel(application) {
    private val productStore = DummyProductStore()
    private val recentlyViewedRepository = RecentlyViewedRepository(application)

    val product: Product
        get() = productStore.findById(productId)

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
            val cartItems = ShoppingCart.getCartItems()
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
            if (lastViewedEntity != null) {
                val lastViewedProduct = productStore.findById(lastViewedEntity.productId)
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
            if (_cartItem.value?.quantity ?: 0 > 0) {
                addProductCount()
            } else {
                ShoppingCart.addProductToCart(productId)
                loadCartItem()
            }
        }
    }

    private fun addProductCount() {
        viewModelScope.launch {
            ShoppingCart.addProductCount(productId)
            loadCartItem()
        }
    }

    fun subtractProductCount() {
        viewModelScope.launch {
            ShoppingCart.subtractProductCount(productId)
            loadCartItem()
        }
    }

    companion object {
        fun factory(application: Application, productId: Int): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductDetailViewModel(application, productId) }
        }
    }
}
