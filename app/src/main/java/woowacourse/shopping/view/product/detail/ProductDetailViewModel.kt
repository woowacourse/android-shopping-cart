package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.domain.Product

class ProductDetailViewModel(
    private val product: Product,
    private val repository: CartProductRepository,
) : ViewModel(),
    ProductDetailEventHandler {
    private var shoppingCartQuantity = 0

    private val _navigateEvent = MutableLiveData<Unit>()
    val navigateEvent: LiveData<Unit> = _navigateEvent

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    init {
        loadQuantity()
    }

    override fun onProductAddClick() {
        val currentQuantity = quantity.value ?: 1
        if (shoppingCartQuantity == 0) {
            repository.insert(product.id, currentQuantity)
        } else {
            repository.updateQuantity(product.id, currentQuantity)
        }
        _navigateEvent.value = Unit
    }

    override fun onQuantityIncreaseClick(item: Product) {
        _quantity.value = quantity.value?.plus(1)
    }

    override fun onQuantityDecreaseClick(item: Product) {
        if ((quantity.value ?: 0) <= 1) return
        _quantity.value = quantity.value?.minus(1)
    }

    private fun loadQuantity() {
        shoppingCartQuantity = repository.getQuantityByProductId(product.id) ?: 0
        _quantity.value = shoppingCartQuantity.coerceAtLeast(1)
    }
}
