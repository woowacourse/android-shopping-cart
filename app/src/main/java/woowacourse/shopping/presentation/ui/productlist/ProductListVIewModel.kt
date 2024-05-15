package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductListRepository

class ProductListVIewModel(
    private val productListRepository: ProductListRepository = DummyProductList,
) : ViewModel(), ProductListActionHandler {
    private val _productList: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val productList: LiveData<List<Product>> get() = _productList
    private val _navigateAction: MutableLiveData<ProductListNavigateAction> = MutableLiveData(null)
    val navigateAction: LiveData<ProductListNavigateAction> get() = _navigateAction

    private val _pageNum: MutableLiveData<Int> = MutableLiveData(INIT_PAGE_NUM)
    val pageNum: LiveData<Int> get() = _pageNum

    init {
        getPagingProduct(INIT_PAGE_NUM)
    }

    private fun getProductList() {
        _productList.value = productListRepository.getProductList()
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
            _productList.value = _productList.value?.plus(pagingProduct.productList)
            _pageNum.value = pagingProduct.currentPage
        }.onFailure {
            // TODO 예외 처리 예정
        }
    }

    fun onClickLoadMoreButton() {
        pageNum.value?.let { num ->
            getPagingProduct(num + 1)
        }
    }

    companion object {
        private const val INIT_PAGE_NUM = 0
        private const val PAGING_SIZE = 8
    }
}
