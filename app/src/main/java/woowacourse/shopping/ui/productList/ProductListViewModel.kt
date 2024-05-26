package woowacourse.shopping.ui.productList

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductCountEvent
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

    private val _productsEvent: MutableLiveData<ProductCountEvent> =
        MutableLiveData(ProductCountEvent.ProductCountAllCleared)
    val productsEvent: LiveData<ProductCountEvent> = _productsEvent

    private var _detailProductDestinationId: MutableSingleLiveData<Long> = MutableSingleLiveData()
    val detailProductDestinationId: SingleLiveData<Long> get() = _detailProductDestinationId

    private var _shoppingCartDestination: MutableSingleLiveData<Boolean> = MutableSingleLiveData()
    val shoppingCartDestination: SingleLiveData<Boolean> get() = _shoppingCartDestination

    fun loadAll() {
        thread {
            if (_loadedProducts.value?.isEmpty() == true) {
                val result: List<Product> = productsRepository.loadAllProducts(currentPage.value!!)
                val totalCartCount = productsRepository.shoppingCartProductQuantity()
                val isLastPage = productsRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())
                val productHistory = productHistoryRepository.loadAllProductHistory()

                uiHandler.post {
                    _loadedProducts.value =
                        _loadedProducts.value?.toMutableList()?.apply {
                            addAll(result)
                        }
                    _cartProductTotalCount.value = totalCartCount
                    _isLastPage.value = isLastPage
                    _productsHistory.value = productHistory
                }
            }
        }
    }

    fun loadNextPageProducts() {
        thread {
            if (isLastPage.value == true) return@thread
            uiHandler.post {
                _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
            }
            val isLastPage = productsRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())
            val result = productsRepository.loadAllProducts(currentPage.value ?: currentPageIsNullException())
            val totalCount = productsRepository.shoppingCartProductQuantity()

            uiHandler.post {
                _isLastPage.value = isLastPage
                _loadedProducts.value =
                    _loadedProducts.value?.toMutableList()?.apply {
                        addAll(result)
                    }
                _cartProductTotalCount.value = totalCount
            }
        }
    }

    fun navigateToShoppingCart() {
        Log.d(TAG, "navigateToShoppingCart: called")
        _shoppingCartDestination.setValue(true)
    }

    override fun onClick(productId: Long) {
        Log.d(TAG, "onClick: called productId: $productId")
        _detailProductDestinationId.setValue(productId)
    }

    override fun onIncrease(productId: Long) {
        thread {
            try {
                productsRepository.increaseShoppingCartProduct(productId)
                Log.d(TAG, "onIncrease: increaseShoppingCartProduct called")
            } catch (e: NoSuchElementException) {
                productsRepository.addShoppingCartProduct(productId)
                Log.d(TAG, "onIncrease: addShoppingCartProduct called")
            } catch (e: Exception) {
                Log.d(TAG, "onIncrease: catch Exception")
            } finally {
                val product = productsRepository.loadProduct(productId)
                val productEvent = ProductCountEvent.ProductCountCountChanged(productId, product.quantity)
                val totalCount = productsRepository.shoppingCartProductQuantity()

                uiHandler.post {
                    _productsEvent.value = productEvent
                    _loadedProducts.value =
                        _loadedProducts.value?.map {
                            if (it.id == productId) {
                                it.copy(quantity = it.quantity + 1)
                            } else {
                                it
                            }
                        }
                    _cartProductTotalCount.value = totalCount
                    Log.d(TAG, "onIncrease: totalCount: $totalCount")
                    Log.d(TAG, "onIncrease: cartProductTotalCount: ${cartProductTotalCount.value}")
                }
            }
        }
    }

    override fun onDecrease(productId: Long) {
        thread {
            val product = productsRepository.loadProduct(productId)
            productsRepository.decreaseShoppingCartProduct(productId)

            val productEvent: ProductCountEvent
            if (product.quantity == 1) {
                productEvent = ProductCountEvent.ProductCountCleared(productId)
            } else {
                productEvent = ProductCountEvent.ProductCountCountChanged(productId, product.quantity - 1)
            }
            val totalCount = productsRepository.shoppingCartProductQuantity()

            uiHandler.post {
                _productsEvent.value = productEvent
                _loadedProducts.value =
                    _loadedProducts.value?.map {
                        if (it.id == productId) {
                            it.copy(quantity = it.quantity - 1)
                        } else {
                            it
                        }
                    }
                _cartProductTotalCount.value = totalCount
                Log.d(TAG, "onDecrease: totalCount: ${cartProductTotalCount.value}")
            }
            return@thread
        }
    }

    private fun productRemoved(
        product: Product,
        productId: Long,
    ): Boolean {
        if (product.quantity == 0) {
            productsRepository.removeShoppingCartProduct(productId)

            _productsEvent.value = ProductCountEvent.ProductCountCleared(productId)

            _loadedProducts.value =
                _loadedProducts.value?.map {
                    if (it.id == productId) {
                        it.copy(quantity = 0)
                    } else {
                        it
                    }
                }
            thread {
                _cartProductTotalCount.value = productsRepository.shoppingCartProductQuantity()
            }
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "ProductListViewModel"
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
    }
}
