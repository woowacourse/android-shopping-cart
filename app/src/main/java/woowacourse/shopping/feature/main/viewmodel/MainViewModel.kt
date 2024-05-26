package woowacourse.shopping.feature.main.viewmodel

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartItemQuantity
import woowacourse.shopping.model.Product

class MainViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _quantities = MutableLiveData<List<CartItemQuantity>>()
    val quantities: LiveData<List<CartItemQuantity>> get() = _quantities

    private val _isSeeMore = MutableLiveData(false)
    val isSeeMore: LiveData<Boolean> get() = _isSeeMore

    private var page = 0

    init {
        loadPage()
    }

    fun loadPage() {
        val products = products.value ?: emptyList()
        _products.value = products + productRepository.findRange(page++, PAGE_SIZE)
        updateQuantities()
    }

    fun addProductToCart(productId: Long) {
        cartRepository.addProduct(productId)
        updateQuantities()
    }

    fun deleteProductToCart(productId: Long) {
        runCatching {
            cartRepository.deleteProduct(productId)
        }.onSuccess {
            updateQuantities()
        }
    }

    fun updateSeeMoreStatus(
        lastPosition: Int,
        totalCount: Int?,
    ) {
        _isSeeMore.value = (lastPosition + 1) % PAGE_SIZE == 0 && lastPosition + 1 == totalCount
    }

    private fun updateQuantities() {
        val products = products.value ?: return
        _quantities.value = cartRepository.findQuantityOfCartItems(products)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
