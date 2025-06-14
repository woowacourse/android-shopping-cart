package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.data.repository.cart.CartRepository
import woowacourse.shopping.data.repository.products.recentlyviewed.RecentlyViewedRepository
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(
    private val cartRepository: CartRepository,
    private val recentlyViewedRepository: RecentlyViewedRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _cartAddedEvent = MutableLiveData<Unit>()
    val cartAddedEvent: LiveData<Unit> get() = _cartAddedEvent

    private val _exitEvent = MutableLiveData<Unit>()
    val exitEvent: LiveData<Unit> get() = _exitEvent

    private val _recentlyViewedProduct = MutableLiveData<Product>()
    val recentlyViewedProduct: LiveData<Product> get() = _recentlyViewedProduct

    private val _recentlyViewedProductEvent = MutableLiveData<Unit>()
    val recentlyViewedProductEvent: LiveData<Unit> get() = _recentlyViewedProductEvent

    fun fetchData(product: Product) {
        recentlyViewedRepository.getLatestViewed { recentlyViewed ->
            if (recentlyViewed != null && recentlyViewed != product) {
                _recentlyViewedProduct.postValue(recentlyViewed)
            }
        }
        _product.value = product
        recentlyViewedRepository.insert(product)
    }

    fun onAddCartClicked(count: Int) {
        product.value?.let {
            cartRepository.addProduct(it, count)
            _cartAddedEvent.value = Unit
        }
    }

    fun onExitClicked() {
        _exitEvent.value = Unit
    }

    fun onRecentlyViewedProductClicked() {
        _recentlyViewedProductEvent.value = Unit
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val cartRepository =
                        (this[APPLICATION_KEY] as ShoppingApplication).cartRepository
                    val recentlyViewedRepository =
                        (this[APPLICATION_KEY] as ShoppingApplication).recentlyViewedRepository
                    ProductDetailViewModel(
                        cartRepository = cartRepository,
                        recentlyViewedRepository = recentlyViewedRepository,
                    )
                }
            }
    }
}
