package woowacourse.shopping.productDetail.mostRecent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import woowacourse.shopping.factory.BaseViewModelFactory
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository

class MostRecentDetailViewModel(application: Application, val productId: Int) :
    AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _cartItem = MutableLiveData<CartItem>()
    val cartItem: LiveData<CartItem> get() = _cartItem

    init {
        loadProduct()
        loadCartItem()
        addProductToRecentlyViewed()
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
            return BaseViewModelFactory { MostRecentDetailViewModel(application, productId) }
        }
    }
}
