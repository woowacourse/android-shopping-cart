package woowacourse.shopping.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.cart.event.CartEventHandler
import woowacourse.shopping.data.cart.CartItemRepository
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.util.SingleLiveEvent

class CartViewModel(
    private val repository: CartItemRepository,
) : ViewModel(),
    CartEventHandler {
    private val _cartProducts = MutableLiveData<List<ProductUiModel>>()
    val cartProducts: LiveData<List<ProductUiModel>> = _cartProducts

    private val _isNextButtonEnabled = MutableLiveData<Boolean>(false)
    val isNextButtonEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isPrevButtonEnabled = MutableLiveData<Boolean>(false)
    val isPrevButtonEnabled: LiveData<Boolean> = _isPrevButtonEnabled

    private val _pageEvent = SingleLiveEvent<Int>()
    val pageEvent: LiveData<Int> = _pageEvent

    private val _product = MutableLiveData<ProductUiModel>()
    val product: LiveData<ProductUiModel> = _product

    private var currentPage: Int = INITIAL_PAGE

    init {
        loadCartProducts()
    }

    override fun onDeleteProduct(cartProduct: ProductUiModel) {
        repository.deleteCartItemById(cartProduct.id) {
            loadCartProducts()
        }
    }

    override fun onNextPage() {
        repository.getAllCartItemSize { size ->
            val lastPage = (size - 1) / PAGE_SIZE
            if (currentPage < lastPage) {
                currentPage++
                _pageEvent.postValue(currentPage)
                loadCartProducts()
            }
        }
    }

    override fun onPrevPage() {
        if (currentPage > 0) {
            currentPage--
            _pageEvent.postValue(currentPage)
            loadCartProducts()
        }
    }

    fun increaseQuantity(product: ProductUiModel) {
        val newProduct = product.copy(quantity = product.quantity + 1)
        repository.updateCartItem(newProduct) {
            _product.postValue(newProduct)
        }
    }

    fun decreaseQuantity(product: ProductUiModel) {
        val newQuantity = if (product.quantity > 1) product.quantity - 1 else 1
        val newProduct = product.copy(quantity = newQuantity)
        repository.updateCartItem(newProduct) {
            _product.postValue(newProduct)
        }
    }

    override fun isNextButtonEnabled(): Boolean = _isNextButtonEnabled.value == true

    override fun isPrevButtonEnabled(): Boolean = _isPrevButtonEnabled.value == true

    override fun isPaginationEnabled(): Boolean = (_isNextButtonEnabled.value == true) || (_isPrevButtonEnabled.value == true)

    override fun getPage(): Int = currentPage

    private fun loadCartProducts(pageSize: Int = PAGE_SIZE) {
        repository.getAllCartItemSize { totalSize ->
            var current = currentPage
            while (current > 0 && current * pageSize >= totalSize) {
                current--
            }
            currentPage = current
            _pageEvent.postValue(current)

            val startIndex = current * pageSize
            val endIndex = minOf(startIndex + pageSize, totalSize)

            repository.subListCartItems(startIndex, endIndex) { products ->
                _cartProducts.postValue(products)
                _isNextButtonEnabled.postValue(current < (totalSize - 1) / pageSize)
                _isPrevButtonEnabled.postValue(current > 0)
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val INITIAL_PAGE = 0

        fun factory(repository: CartItemRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                        return CartViewModel(repository) as T
                    }
                    throw IllegalArgumentException("알 수 없는 ViewModel 클래스입니다.$modelClass")
                }
            }
    }
}
