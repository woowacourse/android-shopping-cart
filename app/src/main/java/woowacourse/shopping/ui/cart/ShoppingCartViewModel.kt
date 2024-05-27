package woowacourse.shopping.ui.cart

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
import woowacourse.shopping.domain.repository.DefaultShoppingProductRepository
import woowacourse.shopping.domain.repository.ShoppingProductsRepository
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener
import kotlin.concurrent.thread

class ShoppingCartViewModel(
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private val uiHandler = Handler(Looper.getMainLooper())

    private var _itemsInCurrentPage = MutableLiveData<List<Product>>()
    val itemsInCurrentPage: LiveData<List<Product>> get() = _itemsInCurrentPage

    private var _isLastPage: MutableLiveData<Boolean> = MutableLiveData()
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private var _deletedItemId: MutableSingleLiveData<Long> = MutableSingleLiveData()
    val deletedItemId: SingleLiveData<Long> get() = _deletedItemId

    fun loadAll() {
        thread {
            val currentItems =
                shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
            val isLastPage =
                shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())

            uiHandler.post {
                _itemsInCurrentPage.value = currentItems
                _isLastPage.value = isLastPage
            }
        }
    }

    fun nextPage() {
        if (isLastPage.value == true) return
        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)

        thread {
            val isLastPage =
                shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())
            val currentItems =
                shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
            uiHandler.post {
                _isLastPage.value = isLastPage
                _itemsInCurrentPage.value = currentItems
            }
        }
    }

    fun previousPage() {
        if (currentPage.value == FIRST_PAGE) return
        _currentPage.value = _currentPage.value?.minus(PAGE_MOVE_COUNT)

        thread {
            val isLastPage =
                shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())
            val currentItems =
                shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
            uiHandler.post {
                _isLastPage.value = isLastPage
                _itemsInCurrentPage.value = currentItems
            }
        }
    }

    fun deleteItem(cartItemId: Long) {
        thread {
            shoppingProductsRepository.removeShoppingCartProduct(cartItemId)
            val currentItems =
                shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
            val isLastPage =
                shoppingProductsRepository.isCartFinalPage(currentPage.value ?: currentPageIsNullException())

            uiHandler.post {
                _itemsInCurrentPage.value = currentItems
                _isLastPage.value = isLastPage
            }
        }
    }

    override fun onClick(productId: Long) {
        _deletedItemId.setValue(productId)
    }

    override fun onIncrease(productId: Long) {
        thread {
            shoppingProductsRepository.increaseShoppingCartProduct(productId)
            val currentItems =
                shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
            uiHandler.post {
                _itemsInCurrentPage.value = currentItems
            }
        }
    }

    override fun onDecrease(productId: Long) {
        thread {
            val product = shoppingProductsRepository.loadProduct(productId)
            if (product.quantity == 1) {
                return@thread
            }

            shoppingProductsRepository.decreaseShoppingCartProduct(productId)
            val currentItems =
                shoppingProductsRepository.loadProductsInCart(page = currentPage.value ?: currentPageIsNullException())
            uiHandler.post {
                _itemsInCurrentPage.value = currentItems
            }
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
        private const val TAG = "ShoppingCartViewModel"

        fun factory(
            shoppingProductsRepository: ShoppingProductsRepository =
                DefaultShoppingProductRepository(
                    productsSource = ShoppingApp.productSource,
                    cartSource = ShoppingApp.cartSource,
                ),
        ): UniversalViewModelFactory =
            UniversalViewModelFactory {
                ShoppingCartViewModel(shoppingProductsRepository)
            }
    }
}
