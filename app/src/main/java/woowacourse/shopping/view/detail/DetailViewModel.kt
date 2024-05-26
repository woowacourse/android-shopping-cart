package woowacourse.shopping.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import woowacourse.shopping.data.db.cart.CartRepository
import woowacourse.shopping.data.db.cart.CartRepository.Companion.DEFAULT_QUANTITY
import woowacourse.shopping.data.db.product.ProductRepository
import woowacourse.shopping.data.db.recent.RecentProductRepository
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.util.Event
import woowacourse.shopping.view.cart.QuantityClickListener
import woowacourse.shopping.view.state.UIState

class DetailViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
    private val productId: Long,
) : ViewModel(), DetailClickListener, QuantityClickListener {
    private val _detailUiState = MutableLiveData<UIState<Product>>(UIState.Empty)
    val detailUiState: LiveData<UIState<Product>>
        get() = _detailUiState

    private val _product: MutableLiveData<Product> = MutableLiveData()
    val product: LiveData<Product>
        get() = _product

    private val _mostRecentProduct: MutableLiveData<RecentProduct?> = MutableLiveData()
    val mostRecentProduct: LiveData<RecentProduct?>
        get() = _mostRecentProduct

    private val _isMostRecentProductVisible = MutableLiveData<Boolean>()

    val isMostRecentProductVisible: LiveData<Boolean>
        get() = _isMostRecentProductVisible

    private val _navigateToCart = MutableLiveData<Event<Boolean>>()
    val navigateToCart: LiveData<Event<Boolean>>
        get() = _navigateToCart

    private val _navigateToRecentDetail = MutableLiveData<Event<Boolean>>()
    val navigateToRecentDetail: LiveData<Event<Boolean>>
        get() = _navigateToRecentDetail

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
            val productData = productRepository.findProductById(productId) ?: return
            _product.value = productData
            _detailUiState.value = UIState.Success(productData)
        } catch (e: Exception) {
            _detailUiState.value = UIState.Error(e)
        }
    }

    fun saveCartItem(quantity: Int) {
        val state = detailUiState.value
        if (state is UIState.Success) {
            cartRepository.save(
                product = state.data,
                quantity = quantity,
            )
        }
    }

    fun saveRecentProduct(isMostRecentProductClicked: Boolean) {
        _mostRecentProduct.value = recentProductRepository.findMostRecentProduct()
        val currentProduct = product.value ?: return
        recentProductRepository.save(currentProduct)
        updateRecentProductVisible(isMostRecentProductClicked)
    }

    fun updateRecentProductVisible(isMostRecentProductClicked: Boolean) {
        _isMostRecentProductVisible.value =
            if (mostRecentProduct.value == null || product.value?.id == mostRecentProduct.value?.productId) {
                false
            } else {
                !isMostRecentProductClicked
            }
    }

    override fun onPutCartButtonClick() {
        val currentQuantity = cartRepository.productQuantity(productId)
        saveCartItem(currentQuantity + (quantity.value ?: DEFAULT_QUANTITY))
        _navigateToCart.value = Event(true)
    }

    override fun onRecentProductClick() {
        _isMostRecentProductVisible.value = false
        _navigateToRecentDetail.value = Event(true)
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
