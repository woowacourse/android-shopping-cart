package woowacourse.shopping.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.MutableSingleLiveData
import woowacourse.shopping.SingleLiveData
import woowacourse.shopping.currentPageIsNullException
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductCountEvent
import woowacourse.shopping.domain.repository.ShoppingProductsRepository

class ProductListViewModel2(
    private val productsRepository: ShoppingProductsRepository,
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(FIRST_PAGE),
) : ViewModel(),
    ProductRecyclerViewAdapter.OnProductItemClickListener,
    ProductRecyclerViewAdapter.OnItemQuantityChangeListener {
    val currentPage: LiveData<Int> get() = _currentPage

    private val _loadedProducts: MutableLiveData<List<Product>> =
        MutableLiveData(
            productsRepository.loadAllProducts(currentPage.value ?: currentPageIsNullException()),
        )
    val loadedProducts: LiveData<List<Product>> get() = _loadedProducts

    private val _cartProductTotalCount: MutableLiveData<Int> =
        MutableLiveData(
            productsRepository.shoppingCartProductQuantity(),
        )
    val cartProductTotalCount: LiveData<Int> get() = _cartProductTotalCount

    private var _isLastPage: MutableLiveData<Boolean> =
        MutableLiveData(
            productsRepository.isFinalPage(currentPage.value ?: currentPageIsNullException()),
        )
    val isLastPage: LiveData<Boolean> get() = _isLastPage

    private val _productsEvent: MutableLiveData<ProductCountEvent> =
        MutableLiveData(ProductCountEvent.ProductCountAllCleared)

    private var _detailProductDestinationId: MutableSingleLiveData<Int> = MutableSingleLiveData()
    val detailProductDestinationId: SingleLiveData<Int> get() = _detailProductDestinationId

    val productsEvent: LiveData<ProductCountEvent> = _productsEvent

    fun loadNextPageProducts() {
        if (isLastPage.value == true) return
        _currentPage.value = _currentPage.value?.plus(PAGE_MOVE_COUNT)
        _isLastPage.value = productsRepository.isFinalPage(currentPage.value ?: currentPageIsNullException())

        val result = productsRepository.loadAllProducts(currentPage.value ?: currentPageIsNullException())

        _loadedProducts.value =
            _loadedProducts.value?.toMutableList()?.apply {
                addAll(result)
            }
        _cartProductTotalCount.value = productsRepository.shoppingCartProductQuantity()
    }

    override fun onClick(productId: Int) {
        _detailProductDestinationId.setValue(productId)
    }

    override fun onIncrease(productId: Int) {
        try {
            productsRepository.increaseShoppingCartProduct(productId)
        } catch (e: NoSuchElementException) {
            productsRepository.addShoppingCartProduct(productId)
        } finally {
            val product = productsRepository.loadProduct(productId)
            _productsEvent.value = ProductCountEvent.ProductCountCountChanged(productId, product.quantity)

            _loadedProducts.value =
                _loadedProducts.value?.map {
                    if (it.id == productId) {
                        it.copy(quantity = it.quantity + 1)
                    } else {
                        it
                    }
                }
            _cartProductTotalCount.value = productsRepository.shoppingCartProductQuantity()
        }
    }

    override fun onDecrease(productId: Int) {
        productsRepository.decreaseShoppingCartProduct(productId)
        val product = productsRepository.loadProduct(productId)
        if (productRemoved(product, productId)) return
        _productsEvent.value = ProductCountEvent.ProductCountCountChanged(productId, product.quantity)

        _loadedProducts.value =
            _loadedProducts.value?.map {
                if (it.id == productId) {
                    it.copy(quantity = it.quantity - 1)
                } else {
                    it
                }
            }

        _cartProductTotalCount.value = productsRepository.shoppingCartProductQuantity()
    }

    private fun productRemoved(
        product: Product,
        productId: Int,
    ): Boolean {
        if (product.quantity == 0) {
            productsRepository.removeShoppingCartProduct(productId)
            _productsEvent.value = ProductCountEvent.ProductCountCleared(productId)
            _loadedProducts.value = currentPage.value?.let { productsRepository.loadAllProducts(it) }
            _cartProductTotalCount.value = productsRepository.shoppingCartProductQuantity()
            return true
        }
        return false
    }

    companion object {
        private const val TAG = "ProductListViewModel2"
        private const val FIRST_PAGE = 1
        private const val PAGE_MOVE_COUNT = 1
    }
}
