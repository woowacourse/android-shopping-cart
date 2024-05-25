package woowacourse.shopping.feature.main.viewmodel

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

    fun loadPage(
        page: Int,
        pageSize: Int,
    ) {
        _products.value = productRepository.findRange(page, pageSize)
        updateQuantities()
    }

    fun addProductToCart(productId: Long) {
        cartRepository.addProduct(productId)
        updateQuantities()
        // Log.d("수량: + 버튼 클릭", "view model ${quantities.value?.find { it.productId == productId }?.quantity?.count}")
    }

    fun deleteProductToCart(productId: Long) {
        runCatching {
            cartRepository.deleteProduct(productId)
        }.onSuccess {
            updateQuantities()
            // Log.d("수량: - 버튼 클릭", "view model ${quantities.value?.find { it.productId == productId }?.quantity?.count}")
        }
    }

    private fun updateQuantities() {
        val products = products.value ?: return
        _quantities.value = cartRepository.findQuantityOfCartItems(products)
        // Log.d("수량", "view model ${quantities.value?.map { it.quantity.count }}")
    }
}
