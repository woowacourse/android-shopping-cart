package woowacourse.shopping.presentation.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.ResultState
import woowacourse.shopping.presentation.SingleLiveData

class ProductViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<ResultState<List<CartItem>>> = MutableLiveData()
    val products: LiveData<ResultState<List<CartItem>>> = _products
    private val _showLoadMore: MutableLiveData<Boolean> = MutableLiveData(true)
    val showLoadMore: LiveData<Boolean> = _showLoadMore
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    private var currentPage = FIRST_PAGE
    private val pageSize = 10

    init {
        fetchData(currentPage)
    }

    fun fetchData(currentPage: Int) {
        productRepository.getPagedProducts(currentPage, pageSize) { result ->
            result
                .onSuccess { cartItems ->
                    _products.postValue(ResultState.Success(cartItems))
                    this.currentPage++
                }.onFailure { e ->
                    Log.e("ProductViewModel", "Failed to load products", e)
                }
        }
    }

    fun loadMore() {
        productRepository.getPagedProducts(currentPage, pageSize) { result ->
            result
                .onSuccess { newItems ->
                    val currentList = (_products.value as? ResultState.Success)?.data.orEmpty()
                    val updatedList = currentList + newItems
                    _products.postValue(ResultState.Success(updatedList))
                    currentPage++
                    _showLoadMore.postValue(updatedList.size < DummyProducts.values.size)
                }.onFailure { e ->
                    Log.e("ProductViewModel", "Failed to load next products", e)
                    _products.postValue(ResultState.Failure())
                }
        }
    }

    fun increaseQuantity(productId: Long) {
        cartRepository.increaseQuantity(productId, 1) { result ->
            result
                .onSuccess {
                    updateQuantity(productId, 1)
                }.onFailure {
                    Log.d("ProductViewModel", "increase fail")
                }
        }
    }

    fun decreaseQuantity(productId: Long) {
        val currentItems = (_products.value as? ResultState.Success)?.data ?: return
        val item = currentItems.find { it.product.productId == productId } ?: return

        if (item.quantity <= 1) {
            cartRepository.deleteProduct(productId) { result ->
                result
                    .onSuccess {
                        updateQuantity(productId, -1)
                    }.onFailure {
                        Log.d("ProductViewModel", "delete fail")
                    }
            }
        } else {
            cartRepository.decreaseQuantity(productId) { result ->
                result
                    .onSuccess {
                        updateQuantity(productId, -1)
                    }.onFailure {
                        Log.d("ProductViewModel", "decrease fail")
                    }
            }
        }
    }

    fun addToCart(cartItem: CartItem) {
        cartRepository.insertProduct(cartItem) { result ->
            result
                .onSuccess {
                    updateQuantity(productId = cartItem.product.productId, 1)
                }.onFailure {
                    Log.d("ProductViewModel", "add to Cart fail")
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
        private const val FIRST_PAGE = 1
    }
}
