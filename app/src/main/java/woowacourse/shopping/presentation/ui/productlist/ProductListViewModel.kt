package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit

class ProductListViewModel(
    private val productListRepository: ProductListRepository = DummyProductList,
) : BaseViewModel(), ProductListActionHandler {
    private val _uiState: MutableLiveData<ProductListUiState> =
        MutableLiveData(ProductListUiState())
    val uiState: LiveData<ProductListUiState> get() = _uiState

    private val _navigateAction: MutableLiveData<Event<ProductListNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductListNavigateAction>> get() = _navigateAction

    init {
        getPagingProduct(INIT_PAGE_NUM)
    }

    override fun onClickProduct(productId: Int) {
        _navigateAction.emit(ProductListNavigateAction.NavigateToProductDetail(productId = productId))
    }

    private fun getPagingProduct(
        page: Int,
        pageSize: Int = PAGING_SIZE,
    ) {
        productListRepository.getPagingProduct(page, pageSize).onSuccess { item ->
            val pagingProduct =
                PagingProduct(
                    currentPage = item.currentPage,
                    productList =
                        _uiState.value?.pagingProduct?.productList?.plus(item.productList)
                            ?: item.productList,
                    last = item.last,
                )

            _uiState.value = _uiState.value?.copy(pagingProduct = pagingProduct)
        }.onFailure { _ ->
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    override fun onClickLoadMoreButton() {
        _uiState.value?.let { state ->
            state.pagingProduct?.let { pagingProduct ->
                getPagingProduct(pagingProduct.currentPage + 1)
            }
        }
    }

    companion object {
        private const val INIT_PAGE_NUM = 0
        private const val PAGING_SIZE = 20
    }
}
