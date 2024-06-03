package woowacourse.shopping.presentation.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
import woowacourse.shopping.presentation.ui.productlist.uimodels.ProductListUiModel
import woowacourse.shopping.presentation.ui.productlist.uimodels.ProductUiModel
import woowacourse.shopping.presentation.ui.productlist.uistates.OrderUiState
import woowacourse.shopping.presentation.ui.productlist.uistates.PagingProductUiState
import woowacourse.shopping.presentation.ui.productlist.uistates.ProductBrowsingHistoryUiState

class ProductListViewModel(
    private val productListRepository: ProductListRepository,
    private val orderRepository: OrderRepository,
    private val historyRepository: ProductBrowsingHistoryRepository,
) : BaseViewModel(), ProductListActionHandler {
    private val _historyUiState: MutableLiveData<ProductBrowsingHistoryUiState> =
        MutableLiveData(ProductBrowsingHistoryUiState.Loading)
    val historyUiState: LiveData<ProductBrowsingHistoryUiState> get() = _historyUiState

    private val _pagingProductUiState: MutableLiveData<PagingProductUiState> =
        MutableLiveData()
    val pagingProductUiState: LiveData<PagingProductUiState> get() = _pagingProductUiState

    private val _orderUiState: MutableLiveData<OrderUiState> =
        MutableLiveData(OrderUiState.Loading)
    val orderUiState: LiveData<OrderUiState> get() = _orderUiState

    private val _navigateAction: MutableLiveData<Event<ProductListNavigateAction>> =
        MutableLiveData(null)
    val navigateAction: LiveData<Event<ProductListNavigateAction>> get() = _navigateAction

    val productListUiModel: MediatorLiveData<ProductListUiModel> = MediatorLiveData()

    init {
        productListUiModel.addSource(pagingProductUiState) {
            val uiModels =
                it.pagingProduct?.productList?.map { product -> ProductUiModel(product, 0) }
                    ?: listOf()
            val oldPagingProductUiModel = productListUiModel.value
            val newProductListUiModel =
                oldPagingProductUiModel?.copy(
                    productUiModels = uiModels,
                    isLastPage = it.pagingProduct?.isLastPage ?: true,
                ) ?: ProductListUiModel(
                    uiModels,
                    it.pagingProduct?.isLastPage ?: true,
                )
            productListUiModel.value = newProductListUiModel
        }

        productListUiModel.addSource(orderUiState) {
            val productList = productListUiModel.value?.productUiModels
            val orders =
                if (it is OrderUiState.Success) it.orders else listOf()
            val productUiModels =
                productList?.map { productUiModel ->
                    val quantity =
                        orders.firstOrNull { order ->
                            productUiModel.product.id == order.product.id
                        }?.quantity ?: 0
                    ProductUiModel(productUiModel.product, quantity)
                } ?: listOf()
            val oldPagingProductUiModel = productListUiModel.value
            val newPagingProductUiModel =
                oldPagingProductUiModel?.copy(
                    productUiModels = productUiModels,
                )
            productListUiModel.value = newPagingProductUiModel
        }
    }

    fun initProductList() {
        initPagingProduct()
        updateOrders()
        updateHistories()
    }

    override fun onClickProduct(productId: Int) {
        _navigateAction.emit(ProductListNavigateAction.NavigateToProductDetail(productId = productId))
    }

    override fun onClickShoppingCart() {
        _navigateAction.emit(ProductListNavigateAction.NavigateToShoppingCart)
    }

    override fun onClickLoadMoreButton() {
        _pagingProductUiState.value?.let { state ->
            state.pagingProduct?.let { pagingProduct ->
                getPagingProduct(pagingProduct.currentPage + 1)
            }
        }
        updateOrders()
    }

    private fun initPagingProduct(pageSize: Int = PAGING_SIZE) {
        productListRepository.getPagingProduct(INIT_PAGE_NUM, pageSize).onSuccess { item ->
            val pagingProduct =
                PagingProduct(
                    currentPage = item.currentPage,
                    productList = item.productList,
                    isLastPage = item.isLastPage,
                )

            _pagingProductUiState.value = PagingProductUiState(pagingProduct, loading = false)
        }.onFailure { _ ->
            _pagingProductUiState.value = PagingProductUiState(failure = true, loading = false)
            showMessage(MessageProvider.DefaultErrorMessage)
        }
    }

    private fun getPagingProduct(
        page: Int,
        pageSize: Int = PAGING_SIZE,
    ) {
        _pagingProductUiState.value = pagingProductUiState.value?.copy(loading = true)
        productListRepository.getPagingProduct(page, pageSize).onSuccess { item ->
            val oldPagingProduct = pagingProductUiState.value?.pagingProduct
            val newPagingProduct =
                PagingProduct(
                    currentPage = item.currentPage,
                    productList =
                        oldPagingProduct?.productList?.plus(item.productList) ?: item.productList,
                    isLastPage = item.isLastPage,
                )
            _pagingProductUiState.value =
                _pagingProductUiState.value?.copy(pagingProduct = newPagingProduct, loading = false)
        }.onFailure { _ ->
            _pagingProductUiState.value = PagingProductUiState(failure = true, loading = false)
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
        orderRepository.plusOrder(product)
        updateOrders()
    }

    override fun onClickMinusOrderButton(productId: Int) {
        productListRepository.findProductById(productId).onSuccess { product ->
            minusOrder(product)
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
        _orderUiState.value = OrderUiState.Success(orders = orders, orderSum = orderSum)
    }

    private fun updateHistories() {
        _historyUiState.value = ProductBrowsingHistoryUiState.Loading
        historyRepository.getHistories(HISTORY_SIZE).onSuccess { histories ->
            _historyUiState.postValue(ProductBrowsingHistoryUiState.Success(histories))
        }.onFailure {
            _historyUiState.postValue(ProductBrowsingHistoryUiState.Failure)
        }
    }

    companion object {
        private const val INIT_PAGE_NUM = 0
        private const val PAGING_SIZE = 20
        private const val HISTORY_SIZE = 10
    }
}
