package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.HistoryRepository
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit
import woowacourse.shopping.presentation.ui.productlist.uimodels.PagingProductUiModel
import woowacourse.shopping.presentation.ui.productlist.uimodels.ProductUiModel

class ProductListViewModel(
    private val productListRepository: ProductListRepository,
    private val orderRepository: OrderRepository,
    private val historyRepository: HistoryRepository,
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
        getHistories()
        makePagingProductUiModels()
    }

    override fun onClickProduct(productId: Int) {
        productListRepository.findProductById(productId).onSuccess { product ->
            putHistory(product)
        }
        _navigateAction.emit(ProductListNavigateAction.NavigateToProductDetail(productId = productId))
    }

    override fun onClickShoppingCart() {
        _navigateAction.emit(ProductListNavigateAction.NavigateToShoppingCart)
    }

    override fun onClickLoadMoreButton() {
        _uiState.value?.let { state ->
            state.pagingProduct?.let { pagingProduct ->
                getPagingProduct(pagingProduct.currentPage + 1)
            }
        }
        getOrders()
        makePagingProductUiModels()
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
            makePagingProductUiModels()
        }.onFailure {
            throw NoSuchElementException()
        }
    }

    private fun plusOrder(product: Product) {
        orderRepository.plusOrder(product)
        getOrders()
    }

    override fun onClickMinusOrderButton(productId: Int) {
        productListRepository.findProductById(productId).onSuccess { product ->
            minusOrder(product)
            makePagingProductUiModels()
        }.onFailure {
            throw NoSuchElementException()
        }
    }

    private fun minusOrder(product: Product) {
        orderRepository.minusOrder(product)
        getOrders()
    }

    private fun getOrders() {
        val orders = orderRepository.getOrders()
        val orderSum = orders.sumOf { it.quantity }
        _uiState.value = _uiState.value?.copy(orders = orders, orderSum = orderSum)
    }

    private fun getHistories() {
        val histories = historyRepository.getHistories(HISTORY_SIZE)
        _uiState.postValue(_uiState.value?.copy(histories = histories))
    }

    private fun putHistory(product: Product) {
        historyRepository.putProductOnHistory(product)
        getHistories()
    }

    private fun makePagingProductUiModels() {
        val pagingProduct = _uiState.value?.pagingProduct ?: return
        val orders = _uiState.value?.orders ?: listOf()
        val uiModels =
            pagingProduct.productList.map { product ->
                val quantity = orders.firstOrNull { product.id == it.product.id }?.quantity ?: 0
                ProductUiModel(product, quantity)
            }
        val pagingProductUiModel =
            PagingProductUiModel(pagingProduct.currentPage, uiModels, pagingProduct.isLastPage)
        _uiState.value = _uiState.value?.copy(pagingProductUiModel = pagingProductUiModel)
    }

    companion object {
        private const val INIT_PAGE_NUM = 0
        private const val PAGING_SIZE = 20
        private const val HISTORY_SIZE = 10
    }
}
