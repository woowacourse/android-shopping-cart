package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.UiState

class ShoppingViewModel(private val repository: ProductRepository = DummyProductRepository()) :
    ViewModel() {
    private val _error: MutableLiveData<Boolean> = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    private var newItemCount: Int = 0

    private var currentPage: Int = 0

    val maxPosition: Int
        get() = currentPage * PAGE_SIZE

    private val _products = MutableLiveData<UiState<List<Product>>>(UiState.None)
    val products get() = _products

    fun loadInitialProductByPage() {
        if (products.value !is UiState.Success<List<Product>>) {
            repository.load(currentPage, PAGE_SIZE).onSuccess {
                _error.value = false
                _products.value = UiState.Success(it)
                newItemCount = it.size
                currentPage++
            }.onFailure {
                _error.value = true
            }
        }
    }

    fun addProductByPage() {
        repository.load(currentPage, PAGE_SIZE).onSuccess {
            _error.value = false
            newItemCount = it.size
            currentPage++
            _products.value = UiState.Success((_products.value as UiState.Success).data + it)
        }.onFailure {
            _error.value = true
        }
    }

    companion object {
        const val LOAD_ERROR = "아이템을 끝까지 불러왔습니다"
        const val PAGE_SIZE = 20
    }
}
