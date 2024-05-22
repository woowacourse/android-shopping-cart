package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.util.Event
import woowacourse.shopping.view.state.UIState

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingRepository,
    private val productId: Long,
) : ViewModel(), DetailClickListener {
    private val _detailUiState = MutableLiveData<UIState<Product>>(UIState.Empty)
    val detailUiState: LiveData<UIState<Product>>
        get() = _detailUiState

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>>
        get() = _navigateToCart

    private lateinit var _product: Product
    val product: Product
        get() = _product

    init {
        loadProduct()
    }

    private fun loadProduct() {
        try {
            val productData = shoppingRepository.findProductById(productId)
            _product = productData
            _detailUiState.value = UIState.Success(product)
        } catch (e: Exception) {
            _detailUiState.value = UIState.Error(e)
        }
    }

    fun createCartItem() {
        val item = detailUiState.value ?: return
        if (item is UIState.Success) {
            cartRepository.insert(
                product = item.data,
                quantity = 1,
            )
        }
    }

    override fun onPutCartButtonClick() {
        createCartItem()
        _navigateToCart.value = Event(true)
    }
}
