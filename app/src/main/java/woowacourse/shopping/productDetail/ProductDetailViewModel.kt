package woowacourse.shopping.productDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.db.recenteProduct.RecentlyViewedProductEntity
import woowacourse.shopping.factory.BaseViewModelFactory
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedRepository

class ProductDetailViewModel(application: Application, val productId: Int) :
    AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)
    private val recentlyViewedRepository = RecentlyViewedRepository(application)

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _cartItem = MutableLiveData<CartItem>()
    val cartItem: LiveData<CartItem> get() = _cartItem

    private val _lastViewedProduct = MediatorLiveData<Product>()
    val lastViewedProduct: LiveData<Product> get() = _lastViewedProduct

    private val _shouldShowLastViewedProduct = MutableLiveData<Boolean>()
    val shouldShowLastViewedProduct: LiveData<Boolean> get() = _shouldShowLastViewedProduct

    private val recentProducts: LiveData<List<RecentlyViewedProductEntity>> =
        recentlyViewedRepository.getRecentProducts(productId)

    init {
        loadProduct()
        loadCartItem()
        addProductToRecentlyViewed()
        observeLastViewedProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }

    private fun loadCartItem() {
        viewModelScope.launch {
            _cartItem.value = productRepository.getCartItemById(productId)
        }
    }

    private fun addProductToRecentlyViewed() {
        viewModelScope.launch {
            productRepository.addProductToRecentlyViewed(productId)
        }
    }

    private fun observeLastViewedProduct() {
        _lastViewedProduct.addSource(recentProducts) { lastViewedEntities ->
            val lastViewedEntity = lastViewedEntities.firstOrNull()
            var lastViewedProduct = Product(0, "", "", 0)
            if (lastViewedEntity != null) {
                viewModelScope.launch {
                    lastViewedProduct = productRepository.getProductById(lastViewedEntity.productId)
                    _lastViewedProduct.value = lastViewedProduct
                    val shouldShow = lastViewedProduct.id != productId
                    _shouldShowLastViewedProduct.value = shouldShow
                }
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
                productRepository.addProductToCart(productId)
                loadCartItem()
            }
        }
    }

    private fun addProductCount() {
        viewModelScope.launch {
            productRepository.addProductCount(productId)
            loadCartItem()
        }
    }

    fun subtractProductCount() {
        viewModelScope.launch {
            productRepository.subtractProductCount(productId)
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
