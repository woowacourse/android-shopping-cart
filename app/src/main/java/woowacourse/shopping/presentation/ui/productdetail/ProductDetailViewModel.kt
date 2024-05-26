package woowacourse.shopping.presentation.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.ui.productdetail.ProductDetailActivity.Companion.PUT_EXTRA_PRODUCT_ID

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productListRepository: ProductListRepository,
    private val orderRepository: OrderRepository,
    private val historyRepository: HistoryRepository,
) : BaseViewModel(), ProductDetailActionHandler {
    private val _price: MutableLiveData<Int> = MutableLiveData()
    val price: LiveData<Int> get() = _price

    private val _quantity: MutableLiveData<Int> = MutableLiveData(1)
    val quantity: LiveData<Int> get() = _quantity

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product> get() = _product

    private val _history: MutableLiveData<History> = MutableLiveData()
    val history: LiveData<History> get() = _history

    init {
        savedStateHandle.get<Int>(PUT_EXTRA_PRODUCT_ID)?.let(::findByProductId)
        getPrice()
        getHistory()
    }

    private fun findByProductId(id: Int) {
        productListRepository.findProductById(id).onSuccess { productValue ->
            _product.value = productValue
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> showMessage(ProductDetailMessage.NoSuchElementErrorMessage)
                else -> showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
    }

    fun onAddToCartButtonClick() {
        product.value?.let { product ->
            quantity.value?.let { quantity ->
                orderRepository.plusOrder(product, quantity)
            }
            showMessage(ProductDetailMessage.AddToCartSuccessMessage)
        }
    }

    override fun onClickPlusOrderButton() {
        _quantity.value = _quantity.value?.plus(1)
        getPrice()
    }

    override fun onClickMinusOrderButton() {
        quantity.value?.let { value ->
            if (value > 1) _quantity.value = value - 1
        }
        getPrice()
    }

    override fun onClickRecentHistory() {
        _product.value = history.value?.product
        _quantity.value = 1
        getPrice()
    }

    private fun getPrice() {
        product.value?.price?.let {
            _price.value = quantity.value?.times(it)
        }
    }

    private fun getHistory() {
        val history = historyRepository.getHistories(2).getOrNull(1)
        _history.value = history
    }
}
