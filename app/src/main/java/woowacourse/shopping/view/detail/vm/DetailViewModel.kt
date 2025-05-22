package woowacourse.shopping.view.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.CartEntry
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    fun load(productId: Long) {
        _product.value = productRepository.getById(productId)
    }

    fun addProduct() {
        val product = product.value ?: return
        val quantity = quantity.value ?: return
        cartRepository.insert(CartEntry(product.id, quantity))
    }
}
