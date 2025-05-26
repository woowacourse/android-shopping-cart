package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import java.time.LocalDateTime

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart> = _cart

    private val _recentProduct = MutableLiveData<RecentProduct>()
    val recentProduct: LiveData<RecentProduct> = _recentProduct

    fun load(productId: Long) {
        cartRepository.getById(productId) { cart: Cart? ->
            val product = productRepository.getById(productId)
            if (cart == null) {
                val newCart = Cart(product, 1)
                addRecentProduct(product)
                _cart.postValue(newCart)
            } else {
                _cart.postValue(cart ?: return@getById)
            }
        }
    }

    private fun addRecentProduct(product: Product) {
        recentProductRepository.insert(RecentProduct(product, LocalDateTime.now())) {
            recentProductRepository.deleteLastByCreatedDateTime {
            }
        }
    }

    fun loadRecentProduct(productId: Long) {
        recentProductRepository.getLatest { recentProduct ->
            if (productRepository.getById(productId) == recentProduct?.product) return@getLatest
            _recentProduct.postValue(recentProduct ?: return@getLatest)
        }
    }

    fun insertCart() {
        cartRepository.insert(cart.value ?: return) {
        }
    }

    fun plusCartQuantity(cart: Cart) {
        val updated = cart.increase()
        cartRepository.update(updated) {
            _cart.postValue(updated)
        }
    }

    fun minusCartQuantity(cart: Cart) {
        val updated = cart.decrease()
        cartRepository.update(updated) {
            if (updated.quantity == 0) {
                cartRepository.deleteById(cart.product.id) {
                    _cart.postValue(updated)
                }
                return@update
            }
            _cart.postValue(updated)
        }
    }
}
