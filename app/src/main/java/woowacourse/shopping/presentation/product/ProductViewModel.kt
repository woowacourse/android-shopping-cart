package woowacourse.shopping.presentation.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.SingleLiveData

class ProductViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products: MutableLiveData<List<CartItem>> = MutableLiveData()
    val products: LiveData<List<CartItem>> = _products
    private val _showLoadMore: MutableLiveData<Boolean> = MutableLiveData(true)
    val showLoadMore: LiveData<Boolean> = _showLoadMore
    private val _toastMessage = SingleLiveData<Int>()
    val toastMessage: LiveData<Int> = _toastMessage

    private var currentPage = 0
    private val pageSize = 10

    init {
        fetchData()
    }

    fun fetchData() {
        productRepository.getPagedProducts(currentPage, pageSize) { result ->
            result
                .onSuccess { cartItems ->
                    _products.postValue(cartItems)
                    currentPage++
                }.onFailure { e ->
                    Log.e("ProductViewModel", "Failed to load products", e)
                }
        }
    }

    fun loadMore() {
        productRepository.getPagedProducts(currentPage, pageSize) { result ->
            result
                .onSuccess { newItems ->
                    val currentList = _products.value.orEmpty()
                    _products.postValue(currentList + newItems)
                    currentPage++
                    _showLoadMore.postValue(
                        (_products.value?.size ?: 0) < DummyProducts.values.size,
                    )
                }.onFailure { e -> Log.e("ProductViewModel", "Failed to load next products", e) }
        }
    }
}
