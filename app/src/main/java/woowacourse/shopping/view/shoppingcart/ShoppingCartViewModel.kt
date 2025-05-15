package woowacourse.shopping.view.shoppingcart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.view.PagedResult

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _removedProduct = MutableLiveData<ShoppingProduct>()
    val removedProduct: LiveData<ShoppingProduct> = _removedProduct

    private val _shoppingProduct = MutableLiveData<PagedResult<ShoppingProduct>>()
    val shoppingProduct: LiveData<PagedResult<ShoppingProduct>> = _shoppingProduct

    private var _currentPage = MutableLiveData(FIRST_PAGE_NUMBER)
    val currentPage: LiveData<Int> = _currentPage

    init {
        loadProducts()
    }

    fun deleteProduct(shoppingProduct: ShoppingProduct) {
        repository.delete(shoppingProduct.id)
        _removedProduct.value = shoppingProduct
    }

    fun loadMoreShoppingProducts() {
        val result =
            repository.getPaged(
                SHOPPING_PRODUCT_SIZE_LIMIT,
                (_currentPage.value ?: FIRST_PAGE_NUMBER) * SHOPPING_PRODUCT_SIZE_LIMIT,
            )
        _shoppingProduct.value = PagedResult(result.items, result.hasNext)
        _currentPage.value = _currentPage.value?.plus(ADD_PAGE_NUMBER)
    }

    fun loadPreviousShoppingProducts() {
        _currentPage.value = _currentPage.value?.minus(ADD_PAGE_NUMBER)
        val result =
            repository.getPaged(
                SHOPPING_PRODUCT_SIZE_LIMIT,
                (_currentPage.value?.minus(ADD_PAGE_NUMBER) ?: FIRST_PAGE_NUMBER) * SHOPPING_PRODUCT_SIZE_LIMIT,
            )
        _shoppingProduct.value = PagedResult(result.items, result.hasNext)
    }

    private fun loadProducts() {
        val result = repository.getPaged(SHOPPING_PRODUCT_SIZE_LIMIT, _currentPage.value ?: FIRST_PAGE_NUMBER)
        _shoppingProduct.value = PagedResult(result.items, result.hasNext)
        _currentPage.value = _currentPage.value?.plus(ADD_PAGE_NUMBER)
    }

    companion object {
        fun provideFactory(
            applicationContext: Context,
            repository: ShoppingCartRepository = ShoppingCartRepositoryImpl(applicationContext),
        ): ViewModelProvider.Factory =
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
