package woowacourse.shopping.presentation.ui.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.remote.DummyProductRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ProductRepository
import woowacourse.shopping.presentation.ui.Error
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.util.Event

class ShoppingViewModel(private val repository: ProductRepository = DummyProductRepository()) :
    ViewModel() {
    private val _error = MutableLiveData<Event<Error>>()
    val error: LiveData<Event<Error>> = _error

    private var newItemCount: Int = 0

    private var currentPage: Int = 0

    val maxPosition: Int
        get() = currentPage * PAGE_SIZE

    private val _products = MutableLiveData<UiState<List<Product>>>(UiState.None)
    val products get() = _products

    fun loadInitialProductByPage() {
        if (products.value !is UiState.Success<List<Product>>) {
            repository.load(currentPage, PAGE_SIZE).onSuccess {
                _products.value = UiState.Success(it)
                newItemCount = it.size
                currentPage++
            }.onFailure {
                _error.value = Event(Error.ProductNotFound)
            }
        }
    }

    fun addProductByPage() {
        repository.load(currentPage, PAGE_SIZE).onSuccess {
            newItemCount = it.size
            currentPage++
            _products.value = UiState.Success((_products.value as UiState.Success).data + it)
        }.onFailure {
            _error.value = Event(Error.AllProductsLoaded)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
