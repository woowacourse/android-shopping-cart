package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import woowacourse.shopping.domain.model.PagingProduct
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.OrderRepository
import woowacourse.shopping.domain.repository.ProductBrowsingHistoryRepository
import woowacourse.shopping.domain.repository.ProductListRepository
import woowacourse.shopping.presentation.base.BaseViewModel
import woowacourse.shopping.presentation.base.Event
import woowacourse.shopping.presentation.base.MessageProvider
import woowacourse.shopping.presentation.base.emit
import woowacourse.shopping.presentation.ui.productlist.uimodels.PagingProductUiModel
import woowacourse.shopping.presentation.ui.productlist.uimodels.ProductUiModel
import woowacourse.shopping.presentation.ui.productlist.uistates.ProductBrowsingHistoryUiState
import woowacourse.shopping.presentation.ui.productlist.uistates.ProductListUiState

class ProductListViewModel(
    private val productListRepository: ProductListRepository,
    private val orderRepository: OrderRepository,
    private val historyRepository: ProductBrowsingHistoryRepository,
) : BaseViewModel(), ProductListActionHandler {
    private val _uiState: MutableLiveData<ProductListUiState> =
        MutableLiveData(ProductListUiState())
    val uiState: LiveData<ProductListUiState> get() = _uiState

    private val _historyUiState: MutableLiveData<ProductBrowsingHistoryUiState> =
        MutableLiveData(ProductBrowsingHistoryUiState.Loading)
    val historyUiState: LiveData<ProductBrowsingHistoryUiState> get() = _historyUiState

    private val _pagingProductUiModel: MutableLiveData<PagingProductUiModel> =
        MutableLiveData()
    val pagingProductUiModel: LiveData<PagingProductUiModel> get() = _pagingProductUiModel

    private val _navigateAction: MutableLiveData<Event<ProductListNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductListNavigateAction>> get() = _navigateAction

    fun initPage() {
        initPagingProduct()
        updateOrders()
        updateHistories()
        makePagingProductUiModels()
    }

    override fun onClickProduct(productId: Int) {
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
        updateOrders()
        makePagingProductUiModels()
    }

    private fun initPagingProduct(pageSize: Int = PAGING_SIZE) {
        if (_uiState.value?.pagingProduct == null) {
            productListRepository.getPagingProduct(INIT_PAGE_NUM, pageSize).onSuccess { item ->
                val pagingProduct =
                    PagingProduct(
                        currentPage = item.currentPage,
                        productList = item.productList,
                        isLastPage = item.isLastPage,
                    )
                _uiState.value = _uiState.value?.copy(pagingProduct = pagingProduct)
            }.onFailure { _ ->
                showMessage(MessageProvider.DefaultErrorMessage)
            }
        }
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
        updateOrders()
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
        updateOrders()
    }

    private fun updateOrders() {
        val orders = orderRepository.getOrders()
        val orderSum = orders.sumOf { it.quantity }
        _uiState.value = _uiState.value?.copy(orders = orders, orderSum = orderSum)
    }

    private fun updateHistories() {
        _historyUiState.value = ProductBrowsingHistoryUiState.Loading
        historyRepository.getHistories(HISTORY_SIZE).onSuccess { histories ->
            _historyUiState.postValue(ProductBrowsingHistoryUiState.Success(histories))
        }.onFailure {
            _historyUiState.postValue(ProductBrowsingHistoryUiState.Failure)
        }
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
        _pagingProductUiModel.value = pagingProductUiModel
    }

    companion object {
        private const val INIT_PAGE_NUM = 0
        private const val PAGING_SIZE = 20
        private const val HISTORY_SIZE = 10
    }
}
