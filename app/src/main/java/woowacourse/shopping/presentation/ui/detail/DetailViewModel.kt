package woowacourse.shopping.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ShoppingItemsRepository
import woowacourse.shopping.presentation.state.UIState

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val shoppingRepository: ShoppingItemsRepository,
    private val productId: Long,
) : ViewModel() {
    private val _detailUiState = MutableLiveData<UIState<Product>>(UIState.Empty)
    val detailUiState: LiveData<UIState<Product>>
        get() = _detailUiState

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

    fun createShoppingCartItem() {
        val item = detailUiState.value ?: return
        if (item is UIState.Success) {
            cartRepository.insert(
                product = item.data,
                quantity = 1,
            )
        }
    }
}
