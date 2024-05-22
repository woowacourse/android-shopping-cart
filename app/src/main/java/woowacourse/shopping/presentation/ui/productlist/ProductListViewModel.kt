package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit

class ProductListViewModel(
    private val productListRepository: ProductListRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) : BaseViewModel(), ProductListActionHandler {
    private val _uiState: MutableLiveData<ProductListUiState> =
        MutableLiveData(ProductListUiState())
    val uiState: LiveData<ProductListUiState> get() = _uiState

    private val _navigateAction: MutableLiveData<Event<ProductListNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductListNavigateAction>> get() = _navigateAction

    fun initPage() {
        getPagingProduct(INIT_PAGE_NUM)
        getOrders()
    }

    override fun onClickProduct(productId: Int) {
        _navigateAction.emit(ProductListNavigateAction.NavigateToProductDetail(productId = productId))
    }

    override fun onClickLoadMoreButton() {
        _uiState.value?.let { state ->
            state.pagingProduct?.let { pagingProduct ->
                getPagingProduct(pagingProduct.currentPage + 1)
            }
        }
        getOrders()
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
                    isLastPage = item.isLastPage,
                )

            _uiState.value = _uiState.value?.copy(pagingProduct = pagingProduct)
        }.onFailure { _ ->
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    override fun onClickPlusOrderButton(productId: Int) {
        productListRepository.findProductById(productId).onSuccess { product ->
            plusOrder(product)
        }.onFailure {
            throw NoSuchElementException()
        }
    }

    private fun plusOrder(product: Product) {
        shoppingCartRepository.plusOrder(product)
        getOrders()
    }

    override fun onClickMinusOrderButton(productId: Int) {
        productListRepository.findProductById(productId).onSuccess { product ->
            minusOrder(product)
        }.onFailure {
            throw NoSuchElementException()
        }
    }

    private fun minusOrder(product: Product) {
        shoppingCartRepository.minusOrder(product)
        getOrders()
    }

    private fun getOrders() {
        val orders = shoppingCartRepository.getOrders()
        _uiState.value = _uiState.value?.copy(orders = orders)
    }

    companion object {
        private const val INIT_PAGE_NUM = 0
        private const val PAGING_SIZE = 20
    }
}
