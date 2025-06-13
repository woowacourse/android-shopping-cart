package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingProduct

class ShoppingCartViewModel(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ViewModel() {
    private var shoppingProducts: List<ShoppingProduct> = listOf()

    private var _currentPage = MutableLiveData(FIRST_PAGE_NUMBER)
    val currentPage: LiveData<Int> = _currentPage

    private var _cacheShoppingCartProduct = MutableLiveData<List<ShoppingProduct>>()
    val cacheShoppingCartProduct: LiveData<List<ShoppingProduct>> = _cacheShoppingCartProduct

    private val loadedPages = mutableSetOf<Int>()

    private var _hasNext = MutableLiveData<Boolean>()
    val hasNext: LiveData<Boolean> = _hasNext

    init {
        loadProducts()
    }

    private fun loadProducts() {
        val page = _currentPage.value ?: FIRST_PAGE_NUMBER
        shoppingCartRepository.getPaged(
            SHOPPING_PRODUCT_SIZE_LIMIT,
            _currentPage.value ?: FIRST_PAGE_NUMBER,
        ) { result: Result<List<ShoppingProduct>> ->
            result
                .onSuccess { shoppingProducts: List<ShoppingProduct> ->
                    this@ShoppingCartViewModel.shoppingProducts = shoppingProducts
                    loadedPages.add(page)
                    cached(shoppingProducts)
                    checkHasNext()
                }.onFailure {
                }
        }
    }

    fun addToShoppingCart(productId: Long) {
        shoppingCartRepository.increaseProduct(productId) { result: Result<Unit> ->
            result.onSuccess {
                shoppingProducts =
                    shoppingProducts.map {
                        if (it.productId == productId) it.copy(quantity = it.quantity?.plus(1)) else it
                    }
                cached(shoppingProducts)
                checkCacheHasNext()
            }
        }
    }

    fun removeToShoppingCart(productId: Long) {
        shoppingCartRepository.decreaseProduct(productId) { result: Result<Unit> ->
            result
                .onSuccess {
                    shoppingProducts =
                        shoppingProducts.mapNotNull {
                            when {
                                it.productId == productId && it.quantity!! > 1 -> it.copy(quantity = it.quantity!! - 1)
                                it.productId == productId && it.quantity == 1 -> null
                                else -> it
                            }
                        }
                    cached(shoppingProducts)
                    checkCacheHasNext()
                }.onFailure {
                }
        }
    }

    fun loadMoreShoppingProducts() {
        _currentPage.value = _currentPage.value?.plus(ADD_PAGE_NUMBER)

        val page = _currentPage.value ?: FIRST_PAGE_NUMBER

        if (loadedPages.contains(page)) {
            cached(shoppingProducts)
            checkCacheHasNext()
            return
        }

        shoppingCartRepository.getPaged(
            SHOPPING_PRODUCT_SIZE_LIMIT,
            (_currentPage.value ?: FIRST_PAGE_NUMBER) * SHOPPING_PRODUCT_SIZE_LIMIT,
        ) { result: Result<List<ShoppingProduct>> ->
            result
                .onSuccess { newShoppingProducts: List<ShoppingProduct> ->
                    val updatedShoppingProducts = shoppingProducts.plus(newShoppingProducts)
                    shoppingProducts = updatedShoppingProducts
                    loadedPages.add(page)
                    cached(updatedShoppingProducts)
                    checkHasNext()
                }.onFailure {
                }
        }
    }

    fun loadPreviousShoppingProducts() {
        _currentPage.value = _currentPage.value?.minus(ADD_PAGE_NUMBER)
        cached(shoppingProducts)
        checkCacheHasNext()
    }

    fun deleteProduct(shoppingProduct: ShoppingProduct) {
        shoppingCartRepository.delete(shoppingProduct.productId) { result: Result<Unit> ->
            result
                .onSuccess {
                    shoppingProducts = shoppingProducts.filter { it != shoppingProduct }
                    validateCurrentPage()
                    checkCacheHasNext()
                }.onFailure {
                }
        }
    }

    private fun checkHasNext() {
        shoppingCartRepository.getAllSize { result: Result<Int> ->
            result
                .onSuccess { it: Int ->
                    val remainingItems = it - ((_currentPage.value?.plus(1))?.times(5) ?: FIRST_PAGE_NUMBER)

                    _hasNext.postValue(remainingItems > 0)
                }.onFailure {
                }
        }
    }

    private fun checkCacheHasNext() {
        val resultSize = shoppingProducts.size

        val remainingItems = resultSize - ((_currentPage.value?.plus(1))?.times(5) ?: FIRST_PAGE_NUMBER)

        _hasNext.postValue(remainingItems > 0)
    }

    private fun validateCurrentPage() {
        cached(shoppingProducts)
        if (_cacheShoppingCartProduct.value == emptyList<Product>() && _currentPage.value != 0) {
            loadPreviousShoppingProducts()
        }
    }

    private fun cached(shoppingProducts: List<ShoppingProduct>) {
        val offset = (_currentPage.value ?: FIRST_PAGE_NUMBER) * SHOPPING_PRODUCT_SIZE_LIMIT
        val cache = shoppingProducts.getCache(SHOPPING_PRODUCT_SIZE_LIMIT, offset)
        _cacheShoppingCartProduct.postValue(cache)
    }

    private fun List<ShoppingProduct>.getCache(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct> {
        val total = this.size

        if (offset >= total) return emptyList()

        val endIndex = (offset + limit).coerceAtMost(total)
        return this.subList(offset, endIndex)
    }

    companion object {
        fun provideFactory(repository: ShoppingCartRepository = ShoppingProvider.shoppingCartRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
                        return ShoppingCartViewModel(repository) as T
                    }
                    throw IllegalArgumentException()
                }
            }

        private const val FIRST_PAGE_NUMBER = 0
        private const val ADD_PAGE_NUMBER = 1
        private const val SHOPPING_PRODUCT_SIZE_LIMIT = 5
    }
}
