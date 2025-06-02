package woowacourse.shopping.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import woowacourse.shopping.data.local.repository.CartRepository
import woowacourse.shopping.data.local.repository.HistoryRepository
import woowacourse.shopping.data.local.repository.ProductRepository
import woowacourse.shopping.domain.product.CartItem
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.utils.QuantityClickListener

class DetailViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val historyRepository: HistoryRepository,
    val product: Product,
) : ViewModel(), QuantityClickListener {

    private val _cartItem = MutableLiveData(CartItem(product.id, product, 1))
    val cartItem: LiveData<CartItem> = _cartItem

    private val _quantity = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    private var _recentProductId: Long? = null

    private val _recentProduct = MutableLiveData<Product>()
    val recentProduct: LiveData<Product> = _recentProduct

    init {
        loadLastItem()
    }

    fun loadLastItem() {
        historyRepository.getRecentProducts(1) {
            _recentProductId = it.first()
        }
        val recentProductId = _recentProductId ?: return
        val recentProduct = productRepository.fetchById(recentProductId)
        _recentProduct.value = recentProduct

        historyRepository.insert(product.id) { }
    }

    override fun onClickIncrease(cartItem: CartItem) {
        _quantity.value = _quantity.value?.plus(1) ?: throw IllegalArgumentException("")
        _cartItem.value = quantity.value?.let { cartItem.copy(quantity = it) }
    }

    override fun onClickDecrease(cartItem: CartItem) {
        _quantity.value = _quantity.value?.minus(1)?.coerceAtLeast(1) ?: throw IllegalArgumentException("")
        _cartItem.value = quantity.value?.let { cartItem.copy(quantity = it) }
    }

    fun onClickAddCart() {
        val productQuantity = quantity.value ?: throw IllegalArgumentException("")
        cartRepository.upsert(CartItem(product.id, product, productQuantity)) { }
    }
}
