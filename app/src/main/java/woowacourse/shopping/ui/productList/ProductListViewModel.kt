package woowacourse.shopping.ui.productList

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.ShoppingApp
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.UniversalViewModelFactory
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.DefaultProductHistoryRepository
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ProductHistoryRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener
import kotlin.concurrent.thread

class ProductListViewModel(
    private val productsRepository: ShoppingProductsRepository,
    private val productHistoryRepository: ProductHistoryRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private val uiHandler = Handler(Looper.getMainLooper())

    private val _loadedProducts: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val loadedProducts: LiveData<List<Product>> get() = _loadedProducts

    private val _productsHistory: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val productsHistory: LiveData<List<Product>> get() = _productsHistory

    private val _cartProductTotalCount: MutableLiveData<Int> = MutableLiveData()
    val cartProductTotalCount: LiveData<Int> get() = _cartProductTotalCount

    private var _isLastPage: MutableLiveData<Boolean> = MutableLiveData()
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private var _detailProductDestinationId: MutableSingleLiveData<Long> = MutableSingleLiveData()
    val detailProductDestinationId: SingleLiveData<Long> get() = _detailProductDestinationId

    private var _shoppingCartDestination: MutableSingleLiveData<Boolean> = MutableSingleLiveData()
    val shoppingCartDestination: SingleLiveData<Boolean> get() = _shoppingCartDestination

    fun loadAll() {
        thread {
            val page = currentPage.value ?: currentPageIsNullException()
            val result = (FIRST_PAGE..page).flatMap { productsRepository.loadAllProducts(it) }
            val totalCartCount = productsRepository.shoppingCartProductQuantity()
            val isLastPage = productsRepository.isFinalPage(page)
            val productHistory = productHistoryRepository.loadAllProductHistory()

            uiHandler.post {
                _loadedProducts.value = result
                _cartProductTotalCount.value = totalCartCount
                _isLastPage.value = isLastPage
                _productsHistory.value = productHistory
            }
        }
    }

    fun loadNextPageProducts() {
        thread {
            if (isLastPage.value == true) return@thread

            val nextPage = _currentPage.value?.plus(PAGE_MOVE_COUNT) ?: currentPageIsNullException()
            val isLastPage = productsRepository.isFinalPage(nextPage)
            val result = productsRepository.loadAllProducts(nextPage)
            val totalCount = productsRepository.shoppingCartProductQuantity()

            uiHandler.post {
                _currentPage.value = nextPage
                _isLastPage.value = isLastPage
                _loadedProducts.value = _loadedProducts.value?.toMutableList()?.apply { addAll(result) }
                _cartProductTotalCount.value = totalCount
            }
        }
    }

    fun navigateToShoppingCart() {
        _shoppingCartDestination.setValue(true)
    }

    override fun onClick(productId: Long) {
        _detailProductDestinationId.setValue(productId)
    }

    override fun onIncrease(productId: Long) {
        thread {
            try {
                productsRepository.increaseShoppingCartProduct(productId)
            } catch (e: NoSuchElementException) {
                productsRepository.addShoppingCartProduct(productId)
            } catch (_: Exception) {
            } finally {
                val totalCount = productsRepository.shoppingCartProductQuantity()

                uiHandler.post {
                    _loadedProducts.value =
                        _loadedProducts.value?.map {
                            if (it.id == productId) {
                                it.copy(quantity = it.quantity + 1)
                            } else {
                                it
                            }
                        }
                    _cartProductTotalCount.value = totalCount
                }
            }
        }
    }

    override fun onDecrease(productId: Long) {
        thread {
            productsRepository.decreaseShoppingCartProduct(productId)

            val totalCount = productsRepository.shoppingCartProductQuantity()

            uiHandler.post {
                _loadedProducts.value =
                    _loadedProducts.value?.map {
                        if (it.id == productId) {
                            it.copy(quantity = it.quantity - 1)
                        } else {
                            it
                        }
                    }
                _cartProductTotalCount.value = totalCount
            }
            return@thread
        }
    }

    companion object {
        private const val TAG = "ProductListViewModel"
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1

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
