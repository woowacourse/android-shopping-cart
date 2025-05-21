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
    private val _navigateEvent = MutableLiveData<Unit>()
    val navigateEvent: LiveData<Unit> = _navigateEvent

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    init {
        loadQuantity()
    }

    override fun onProductAddClick() {
        repository.insert(product.id)
        _navigateEvent.value = Unit
    }

    override fun onQuantityIncreaseClick(item: Product) {
        _quantity.value = quantity.value?.plus(1)
    }

    override fun onQuantityDecreaseClick(item: Product) {
        if ((quantity.value ?: 0) == 0) return
        _quantity.value = quantity.value?.minus(1)
    }

    private fun loadQuantity() {
        _quantity.value = repository.getQuantityByProductId(product.id) ?: 0
    }
}
