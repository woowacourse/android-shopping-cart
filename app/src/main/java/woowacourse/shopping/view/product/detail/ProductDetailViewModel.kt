package woowacourse.shopping.view.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartProductRepository
import woowacourse.shopping.data.recent.RecentProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct

class ProductDetailViewModel(
    private val product: Product,
    private val cartProductRepository: CartProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel(),
    ProductDetailEventHandler {
    private var shoppingCartQuantity = 0
    var lastProduct: RecentProduct? = null
        private set

    private val _navigateEvent = MutableLiveData<Unit>()
    val navigateEvent: LiveData<Unit> = _navigateEvent

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    init {
        loadQuantity()
        loadLastProduct()
        recentProductRepository.replaceRecentProduct(product.id)
    }

    override fun onProductAddClick() {
        val currentQuantity = quantity.value ?: 1
        if (shoppingCartQuantity == 0) {
            cartProductRepository.insert(product.id, currentQuantity)
        } else {
            cartProductRepository.updateQuantity(product.id, shoppingCartQuantity + currentQuantity)
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
        shoppingCartQuantity = cartProductRepository.getQuantityByProductId(product.id) ?: 0
    }

    private fun loadLastProduct() {
        lastProduct = recentProductRepository.getLastProduct()
        if (lastProduct?.product?.id == product.id) {
            lastProduct = null
        }
    }
}
