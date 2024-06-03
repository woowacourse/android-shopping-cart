package woowacourse.shopping.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener
import woowacourse.shopping.ui.productList.event.ProductListError
import woowacourse.shopping.ui.productList.event.ProductListNavigationEvent

class ProductListViewModel(
    private val productsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    val currentPage: LiveData<Int> get() = _currentPage

    val uiState: DefaultUiState = DefaultUiState()

    private var _errorEvent = MutableSingleLiveData<ProductListError>()
    val errorEvent: SingleLiveData<ProductListError> get() = _errorEvent

    private var _navigationEvent = MutableSingleLiveData<ProductListNavigationEvent>()
    val navigationEvent: SingleLiveData<ProductListNavigationEvent> get() = _navigationEvent


    fun loadAll() {
        val page = currentPage.value ?: currentPageIsNullException()
        loadAllProducts(page)
        loadFinalPage(page)
        calculateProductsQuantityInCart()
        loadProductsHistory()
    }

    private fun loadAllProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult(page) { result ->
            result.onSuccess {
                uiState.loadedProducts.postValue(it)
            }.onFailure {
                _errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    private fun loadFinalPage(page: Int) {
        productsRepository.isFinalPageAsyncResult(page) { result ->
            result.onSuccess {
                uiState.isLastPage.postValue(it)
            }.onFailure {
                _errorEvent.postValue(ProductListError.FinalPage)
            }
        }
    }

    private fun calculateProductsQuantityInCart() {
        productsRepository.shoppingCartProductQuantityAsyncResult { result ->
            result.onSuccess {
                uiState.cartProductTotalCount.postValue(it)
            }.onFailure {
                _errorEvent.postValue(ProductListError.CartProductQuantity)
            }
        }
    }

    private fun loadProductsHistory() {
        productHistoryRepository.loadAllProductHistoryAsyncResult { result ->
            result.onSuccess {
                uiState.productsHistory.postValue(it)
            }.onFailure {
                _errorEvent.postValue(ProductListError.LoadProductHistory)
            }
        }
    }

    fun loadNextPageProducts() {
        if (uiState.isLastPage.value == true) return

        val nextPage = _currentPage.value?.plus(PAGE_MOVE_COUNT) ?: currentPageIsNullException()
        _currentPage.postValue(nextPage)

        loadFinalPage(nextPage)
        addNextPageProducts(nextPage)
        calculateProductsQuantityInCart()
    }

    private fun addNextPageProducts(page: Int) {
        productsRepository.loadAllProductsAsyncResult(page) { result ->
            result.onSuccess {
                uiState.loadedProducts.postValue(uiState.loadedProducts.value?.toMutableList()?.apply { addAll(it) })
            }.onFailure {
                _errorEvent.postValue(ProductListError.LoadProducts)
            }
        }
    }

    fun navigateToShoppingCart() {
        _navigationEvent.setValue(ProductListNavigationEvent.ShoppingCart)
    }

    override fun onClick(productId: Long) {
        _navigationEvent.setValue(ProductListNavigationEvent.ProductDetail(productId))
    }

    override fun onAdd(productId: Long) {
        productsRepository.addShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                changeProductQuantity(productId, INCREASE_AMOUNT)
                calculateProductsQuantityInCart()
            }.onFailure {
                _errorEvent.postValue(ProductListError.AddProductInCart)
            }
        }
    }

    override fun onIncrease(productId: Long) {
        productsRepository.increaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                changeProductQuantity(productId, INCREASE_AMOUNT)
                calculateProductsQuantityInCart()
            }.onFailure {
                _errorEvent.postValue(ProductListError.UpdateProductQuantity)
            }
        }
    }

    override fun onDecrease(productId: Long) {
        productsRepository.decreaseShoppingCartProductAsyncResult(productId) { result ->
            result.onSuccess {
                changeProductQuantity(productId, DECREASE_AMOUNT)
                calculateProductsQuantityInCart()
            }.onFailure {
                _errorEvent.postValue(ProductListError.UpdateProductQuantity)
            }
        }
    }

    private fun changeProductQuantity(
        productId: Long,
        changeAmount: Int,
    ) {
        uiState.loadedProducts.postValue(
            uiState.loadedProducts.value?.map {
                if (it.id == productId) {
                    it.copy(quantity = it.quantity + changeAmount)
                } else {
                    it
                }
            },
        )
    }

    companion object {
        private const val TAG = "ProductListViewModel"
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
        private const val INCREASE_AMOUNT = 1
        private const val DECREASE_AMOUNT = -1

        fun factory(
            productRepository: ShoppingProductsRepository =
                DefaultShoppingProductRepository(
                    ShoppingApp.productSource,
                    ShoppingApp.cartSource,
                ),
            historyRepository: ProductHistoryRepository =
                DefaultProductHistoryRepository(
                    ShoppingApp.historySource,
                    ShoppingApp.productSource,
                ),
        ): UniversalViewModelFactory =
            UniversalViewModelFactory {
                ProductListViewModel(productRepository, historyRepository)
            }
    }
}
