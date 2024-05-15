package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.repository.ProductListRepository

class ProductListViewModel(
    private val productListRepository: ProductListRepository = DummyProductList,
) : ViewModel(), ProductListActionHandler {
    private val _pagingProduct: MutableLiveData<PagingProduct> = MutableLiveData(null)
    val pagingProduct: LiveData<PagingProduct> get() = _pagingProduct

    private val _navigateAction: MutableLiveData<ProductListNavigateAction> = MutableLiveData(null)
    val navigateAction: LiveData<ProductListNavigateAction> get() = _navigateAction

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> get() = _message

    init {
        getPagingProduct(INIT_PAGE_NUM)
    }

    override fun onClickProduct(productId: Int) {
        _navigateAction.value =
            ProductListNavigateAction.NavigateToProductDetail(productId = productId)
    }

    private fun getPagingProduct(
        page: Int,
        pageSize: Int = PAGING_SIZE,
    ) {
        productListRepository.getPagingProduct(page, pageSize).onSuccess { pagingProduct ->
            _pagingProduct.value =
                PagingProduct(
                    currentPage = pagingProduct.currentPage,
                    productList =
                        _pagingProduct.value?.productList?.plus(pagingProduct.productList)
                            ?: pagingProduct.productList,
                    last = pagingProduct.last,
                )
        }.onFailure { e ->
            _message.value = e.message
        }
    }

    fun onClickLoadMoreButton() {
        pagingProduct.value?.let { pagingProduct ->
            getPagingProduct(pagingProduct.currentPage + 1)
        }
    }

    companion object {
        private const val INIT_PAGE_NUM = 0
        private const val PAGING_SIZE = 8
    }
}
