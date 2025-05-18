package woowacourse.shopping.view.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.ShoppingProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.domain.ShoppingProduct

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _removedProduct = MutableLiveData<ShoppingProduct>()
    val removedProduct: LiveData<ShoppingProduct> = _removedProduct

    private var shoppingProducts: List<ShoppingProduct> = listOf()

    private var _currentPage = MutableLiveData(FIRST_PAGE_NUMBER)
    val currentPage: LiveData<Int> = _currentPage

    private var _cacheShoppingCartProduct = MutableLiveData<List<ShoppingProduct>>()
    val cacheShoppingCartProduct: LiveData<List<ShoppingProduct>> = _cacheShoppingCartProduct

    private val loadedPages = mutableSetOf<Int>()

    init {
        loadProducts()
    }

    fun deleteProduct(shoppingProduct: ShoppingProduct) {
        repository.delete(shoppingProduct.id)
        _removedProduct.value = shoppingProduct
        shoppingProducts = shoppingProducts.filter { it != shoppingProduct }
    }

    fun loadMoreShoppingProducts() {
        _currentPage.value = _currentPage.value?.plus(ADD_PAGE_NUMBER)

        val page = _currentPage.value ?: FIRST_PAGE_NUMBER

        if (loadedPages.contains(page)) {
            cached()
            return
        }

        val result =
            repository.getPaged(
                SHOPPING_PRODUCT_SIZE_LIMIT,
                (_currentPage.value ?: FIRST_PAGE_NUMBER) * SHOPPING_PRODUCT_SIZE_LIMIT,
            )

        shoppingProducts = shoppingProducts.plus(result)
        loadedPages.add(page)
        cached()
    }

    fun loadPreviousShoppingProducts() {
        _currentPage.value = _currentPage.value?.minus(ADD_PAGE_NUMBER)
        cached()
    }

    private fun loadProducts() {
        val page = _currentPage.value ?: FIRST_PAGE_NUMBER
        val result = repository.getPaged(SHOPPING_PRODUCT_SIZE_LIMIT, _currentPage.value ?: FIRST_PAGE_NUMBER)
        shoppingProducts = result
        loadedPages.add(page)
        cached()
    }

    private fun cached() {
        _cacheShoppingCartProduct.value =
            shoppingProducts.getCache(
                SHOPPING_PRODUCT_SIZE_LIMIT,
                (_currentPage.value ?: FIRST_PAGE_NUMBER) * SHOPPING_PRODUCT_SIZE_LIMIT,
            )
    }

    private fun List<ShoppingProduct>.getCache(
        limit: Int,
        offset: Int,
    ): List<ShoppingProduct> {
        val total = this.size
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
