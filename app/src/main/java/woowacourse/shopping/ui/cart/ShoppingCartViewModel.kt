package woowacourse.shopping.ui.cart

import android.util.Log
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

class ShoppingCartViewModel(
    private val shoppingProductsRepository: ShoppingProductsRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(), OnProductItemClickListener, OnItemQuantityChangeListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private var _itemsInCurrentPage = MutableLiveData<List<Product>>()
    val itemsInCurrentPage: LiveData<List<Product>> get() = _itemsInCurrentPage

    private var _isLastPage: MutableLiveData<Boolean> = MutableLiveData()
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private var _deletedItemId: MutableSingleLiveData<Long> = MutableSingleLiveData()
    val deletedItemId: SingleLiveData<Long> get() = _deletedItemId

    fun loadAll() {
        val nowPage = currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
            _itemsInCurrentPage.postValue(products)
        }
        shoppingProductsRepository.isCartFinalPageAsync(nowPage) {
            Log.d(TAG, "loadAll: nowPage: $nowPage")
            _isLastPage.postValue(it)
        }
    }

    fun nextPage() {
        if (isLastPage.value == true) return
        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
        val nowPage = currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.isCartFinalPageAsync(page = nowPage) {
            _isLastPage.postValue(it)
        }
        shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
            _itemsInCurrentPage.postValue(products)
        }
    }

    fun previousPage() {
        if (currentPage.value == FIRST_PAGE) return
        _currentPage.value = _currentPage.value?.minus(PAGE_MOVE_COUNT)

        val nowPage = currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.isCartFinalPageAsync(nowPage) {
            _isLastPage.postValue(it)
        }
        shoppingProductsRepository.loadProductsInCartAsync(nowPage) { products ->
            _itemsInCurrentPage.postValue(products)
        }
    }

    fun deleteItem(cartItemId: Long) {
        val nowPage = currentPage.value ?: currentPageIsNullException()

        shoppingProductsRepository.removeShoppingCartProductAsync(cartItemId) {
            shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
                _itemsInCurrentPage.postValue(products)
            }
            shoppingProductsRepository.isCartFinalPageAsync(nowPage) {
                _isLastPage.postValue(it)
            }
        }
    }

    override fun onClick(productId: Long) {
        _deletedItemId.setValue(productId)
    }

    override fun onIncrease(productId: Long) {
        val nowPage = currentPage.value ?: currentPageIsNullException()
        shoppingProductsRepository.increaseShoppingCartProductAsync(productId) {
            shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
                _itemsInCurrentPage.postValue(products)
            }
        }
    }

    override fun onDecrease(productId: Long) {
        shoppingProductsRepository.loadProductAsync(productId) { product ->
            if (product.quantity == 1) return@loadProductAsync

            shoppingProductsRepository.decreaseShoppingCartProductAsync(productId) {
                val nowPage = currentPage.value ?: currentPageIsNullException()
                shoppingProductsRepository.loadProductsInCartAsync(page = nowPage) { products ->
                    _itemsInCurrentPage.postValue(products)
                }
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
