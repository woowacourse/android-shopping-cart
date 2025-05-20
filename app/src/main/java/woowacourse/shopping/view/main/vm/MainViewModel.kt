package woowacourse.shopping.view.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.repository.ProductRepository

class MainViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData(ProductUiState())
    val uiState: LiveData<ProductUiState> = _uiState

    init {
        loadProducts()
    }

    fun loadProducts() {
        val newPage = _uiState.value?.itemCount()?.div(PAGE_SIZE) ?: 0

        val result = productRepository.loadSinglePage(newPage, PAGE_SIZE)

        _uiState.value =
            _uiState.value?.addItems(
                result.products,
                result.hasNextPage,
            )
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
