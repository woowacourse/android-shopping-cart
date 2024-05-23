package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.utils.NoSuchDataException
import woowacourse.shopping.view.CountActionHandler
import woowacourse.shopping.view.Event

class ProductDetailViewModel(
    private val repository: ProductRepository,
    private val productId: Long,
) : ViewModel(), DetailActionHandler, CountActionHandler {
    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _cartItemSavedState: MutableLiveData<ProductDetailState> = MutableLiveData()
    val cartItemSavedState: LiveData<ProductDetailState> get() = _cartItemSavedState

    private val _navigateToBack = MutableLiveData<Event<Boolean>>()
    val navigateToBack: LiveData<Event<Boolean>> get() = _navigateToBack

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> = _quantity

    val totalPrice: LiveData<Int> =
        quantity.switchMap { quantityValue ->
            val price = product.value?.price ?: 1
            MutableLiveData(quantityValue * price)
        }

    init {
        loadProductItem()
    }

    private fun loadProductItem() {
        _product.value = repository.getProduct(productId)
    }

    fun addShoppingCartItem() {
        runCatching {
            val selected = product.value ?: throw NoSuchDataException()
            repository.addCartItem(selected)
        }.onSuccess {
            _cartItemSavedState.value = ProductDetailState.Success
        }.onFailure {
            _cartItemSavedState.value = ProductDetailState.Fail
        }
    }

    override fun onCloseButtonClicked() {
        _navigateToBack.value = Event(true)
    }

    override fun onAddCartButtonClicked() {
        addShoppingCartItem()
    }

    override fun onIncreaseQuantityButtonClicked() {
        _quantity.value = _quantity.value?.plus(1)
    }

    override fun onDecreaseQuantityButtonClicked() {
        _quantity.value = _quantity.value?.minus(1)
    }
}
