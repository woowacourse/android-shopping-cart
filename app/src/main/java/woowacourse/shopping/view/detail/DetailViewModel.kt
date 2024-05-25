package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.CartRepository.Companion.DEFAULT_QUANTITY
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.util.Event
import woowacourse.shopping.view.cart.QuantityClickListener
import woowacourse.shopping.view.state.UIState

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingRepository,
    private val productId: Long,
) : ViewModel(), DetailClickListener, QuantityClickListener {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product>
        get() = _product

    private val _detailUiState = MutableLiveData<UIState<Product>>(UIState.Empty)
    val detailUiState: LiveData<UIState<Product>>
        get() = _detailUiState

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>>
        get() = _navigateToCart

    private val _isFinishButtonClicked = MutableLiveData<Event<Boolean>>()
    val isFinishButtonClicked: LiveData<Event<Boolean>>
        get() = _isFinishButtonClicked

    private var _quantity = MutableLiveData(1)
    val quantity: LiveData<Int>
        get() = _quantity

    val totalPrice: LiveData<Long> =
        quantity.map { quantityValue ->
            product.value?.price?.times(quantityValue) ?: 0
        }

    init {
        loadProduct()
    }

    private fun loadProduct() {
        try {
            val productData = shoppingRepository.findProductById(productId)
            _product.value = productData
            _detailUiState.value = UIState.Success(productData)
        } catch (e: Exception) {
            _detailUiState.value = UIState.Error(e)
        }
    }

    fun createCartItem(quantity: Int) {
        val state = detailUiState.value
        if (state is UIState.Success) {
            cartRepository.save(
                product = state.data,
                quantity = quantity,
            )
        }
    }

    override fun onPutCartButtonClick() {
        val currentQuantity = cartRepository.productQuantity(productId)
        createCartItem(currentQuantity + (quantity.value ?: DEFAULT_QUANTITY))
        _navigateToCart.value = Event(true)
    }

    override fun onQuantityPlusButtonClick(productId: Long) {
        _quantity.value = quantity.value?.plus(1)
    }

    override fun onQuqntityMinusButtonClick(productId: Long) {
        _quantity.value = (quantity.value?.minus(1))?.coerceAtLeast(1)
    }

    override fun onFinishButtonClick() {
        _isFinishButtonClicked.value = Event(true)
    }
}
