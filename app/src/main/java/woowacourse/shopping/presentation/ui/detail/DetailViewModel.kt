package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val productId: Long,
) : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    init {
        loadProductData()
    }

    private fun loadProductData() {
        _product.postValue(shoppingRepository.findProductItem(productId))
    }

    fun createShoppingCartItem() {
        val product = product.value ?: return
        cartRepository.insert(product = product, quantity = 1)
    }
}
