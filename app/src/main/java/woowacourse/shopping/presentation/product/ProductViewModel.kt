package woowacourse.shopping.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.R
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.SingleLiveData

class ProductViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val recentProductRepository: RecentProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<ResultState<List<CartItem>>> = MutableLiveData()
    val products: LiveData<ResultState<List<CartItem>>> = _products
    private val _recentProducts: MutableLiveData<ResultState<List<Product>>> = MutableLiveData()
    val recentProducts: LiveData<ResultState<List<Product>>> = _recentProducts
    private val _cartItemCount = MutableLiveData<Int>()
    val cartItemCount: LiveData<Int> = _cartItemCount
    private val _showLoadMore: MutableLiveData<Boolean> = MutableLiveData(true)
    val showLoadMore: LiveData<Boolean> = _showLoadMore
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    private var currentPage = FIRST_PAGE
    private val pageSize = 10

    init {
        fetchData(currentPage)
        fetchCartItemCount()
    }

    fun fetchData(currentPage: Int) {
        productRepository.getPagedProducts(currentPage, pageSize) { result ->
            result
                .onSuccess { cartItems ->
                    _products.postValue(ResultState.Success(cartItems))
                    this.currentPage = currentPage
                }.onFailure {
                    _toastMessage.postValue(R.string.product_toast_load_failure)
                }
        }

        recentProductRepository.getRecentProducts { result ->
            result
                .onSuccess { products ->
                    _recentProducts.postValue(ResultState.Success(products))
                }.onFailure {
                    _toastMessage.postValue(R.string.product_toast_load_failure)
                }
        }
    }

    fun fetchCartItemCount() {
        cartRepository.getTotalQuantity { result ->
            result
                .onSuccess { count ->
                    _cartItemCount.postValue(count ?: 0)
                }.onFailure {
                    _toastMessage.postValue(R.string.product_toast_load_total_cart_quantity_fail)
                }
        }
    }

    fun loadMore() {
        this.currentPage++
        productRepository.getPagedProducts(currentPage, pageSize) { result ->
            result.fold(
                onSuccess = { newItems ->
                    val currentList = (_products.value as? ResultState.Success)?.data.orEmpty()
                    val updatedList = currentList + newItems
                    _products.postValue(ResultState.Success(updatedList))
                    _showLoadMore.postValue(updatedList.size < DummyProducts.values.size)
                },
                onFailure = {
                    _products.postValue(ResultState.Failure())
                },
            )
        }
    }

    fun increaseQuantity(productId: Long) {
        cartRepository.increaseQuantity(productId, 1) { result ->
            result
                .onSuccess {
                    updateQuantity(productId, 1)
                    fetchCartItemCount()
                }.onFailure {
                    _toastMessage.value = R.string.product_toast_increase_fail
                }
        }
    }

    fun decreaseQuantity(productId: Long) {
        val currentItems = (_products.value as? ResultState.Success)?.data ?: return
        val item = currentItems.find { it.product.productId == productId } ?: return

        if (item.quantity == 1) {
            cartRepository.deleteProduct(productId) { result ->
                result
                    .onSuccess {
                        updateQuantity(productId, -1)
                        fetchCartItemCount()
                    }.onFailure {
                        _toastMessage.value = R.string.product_toast_delete_fail
                    }
            }
        } else {
            cartRepository.decreaseQuantity(productId) { result ->
                result
                    .onSuccess {
                        updateQuantity(productId, -1)
                        fetchCartItemCount()
                    }.onFailure {
                        _toastMessage.value = R.string.product_toast_decrease_fail
                    }
            }
        }
    }

    fun addToCart(cartItem: CartItem) {
        cartRepository.insertProduct(cartItem) { result ->
            result
                .onSuccess {
                    updateQuantity(productId = cartItem.product.productId, 1)
                    fetchCartItemCount()
                }.onFailure {
                    _toastMessage.value = R.string.product_toast_add_cart_fail
                }
        }
    }

    private fun updateQuantity(
        productId: Long,
        delta: Int,
    ) {
        val currentItems = (_products.value as? ResultState.Success)?.data ?: return
        val updatedItem =
            currentItems.map {
                if (it.product.productId == productId) it.copy(quantity = it.quantity + delta) else it
            }
        _products.postValue(ResultState.Success(updatedItem))
    }

    companion object {
        private const val FIRST_PAGE = 0
    }
}
