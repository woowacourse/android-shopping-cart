package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.providers.RepositoryProvider

class CartViewModel(
    private val repository: CartRepository,
) : ViewModel() {
    private val _cartProducts = MutableLiveData<List<CartProduct>>(emptyList())
    val cartProducts: LiveData<List<CartProduct>> get() = _cartProducts

    private val _pageNumber = MutableLiveData(1)
    val pageNumber: LiveData<Int> get() = _pageNumber

    val isFirstPage: Boolean
        get() = pageNumber.value == 1

    var isLastPage: Boolean = false
        private set

    private val _productsQuantity = mutableMapOf<Long, MutableLiveData<Int>>()
    val productsQuantity: Map<Long, LiveData<Int>> get() = _productsQuantity
    private val _productsTotalPrice = mutableMapOf<Long, MutableLiveData<Int>>()
    val productsTotalPrice: Map<Long, LiveData<Int>> get() = _productsTotalPrice

    init {
        loadCartProducts()
    }

    fun loadCartProducts() {
        val pageNumber = pageNumber.value ?: 1
        repository.fetchInRange(PAGE_FETCH_SIZE, (pageNumber - 1) * PAGE_SIZE) { products ->
            isLastPage = products.size != PAGE_FETCH_SIZE

            initQuantityAndPrice(products)

            val visibleProductsSize = PAGE_SIZE.coerceAtMost(products.size)
            handleUpdateItems(visibleProductsSize, products)
        }
    }

    fun isVisiblePagination(): Boolean {
        return !(isLastPage && pageNumber.value == 1)
    }

    fun moveToPrevious() {
        if (pageNumber.value != 1) {
            _pageNumber.postValue(_pageNumber.value?.minus(1))
        }
    }

    fun moveToNext() {
        if (!isLastPage) {
            _pageNumber.value = _pageNumber.value?.plus(1)
        }
    }

    fun deleteProduct(cartId: Long) {
        repository.delete(cartId) {
            loadCartProducts()
        }
    }

    private fun initQuantityAndPrice(products: List<CartProduct>) {
        products.forEach { product ->
            _productsQuantity[product.id!!] = MutableLiveData(product.quantity)
            _productsTotalPrice[product.id] = MutableLiveData(product.totalPrice())
        }
    }

    private fun handleUpdateItems(
        visibleProductsSize: Int,
        products: List<CartProduct>
    ) {
        if (hasPages(visibleProductsSize)) {
            moveToPrevious()
            return
        }

        val updateItems = products.take(visibleProductsSize)
        _cartProducts.postValue(updateItems)
    }

    private fun hasPages(visibleProductsSize: Int) = visibleProductsSize == 0 && !isFirstPage

    fun increaseQuantity(cartId: Long) {
        // TODO: Result 처리하는 부분 함수 하나로 만들어서 간략하게 만들어보기
        repository.updateQuantity(cartId, 1) { result ->
            val cartProduct = cartProducts.value?.first { it.id == cartId }
            cartProduct?.increase()

            when {
                result.isSuccess -> {
                    _productsQuantity[cartId]?.postValue(cartProduct?.quantity)
                    _productsTotalPrice[cartId]?.postValue(cartProduct?.totalPrice())
                }

                result.isFailure -> {
                    // TODO: 쿼리문 실패
                }
            }
        }
    }

    fun decreaseQuantity(cartId: Long) {
        if (_productsQuantity[cartId]?.value == 1) return // TODO: 토스트로 장바구니에서 제거하고 싶으면 x 누르라고 하기

        repository.updateQuantity(cartId, -1) { result ->
            val cartProduct = cartProducts.value?.first { it.id == cartId }
            cartProduct?.decrease()

            when {
                result.isSuccess -> {
                    _productsQuantity[cartId]?.postValue(cartProduct?.quantity)
                    _productsTotalPrice[cartId]?.postValue(cartProduct?.totalPrice())
                }

                result.isFailure -> {
                    // TODO: 쿼리문 실패
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CartViewModel(RepositoryProvider.provideCartRepository()) as T
                }
            }
        private const val PAGE_FETCH_SIZE = 6
        private const val PAGE_SIZE = 5
    }
}
