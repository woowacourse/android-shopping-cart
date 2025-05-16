package woowacourse.shopping.view.cart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepository
import woowacourse.shopping.data.shoppingcart.ShoppingCartRepositoryImpl
import woowacourse.shopping.domain.ShoppingProduct

class ShoppingCartViewModel(
    private val repository: ShoppingCartRepository,
) : ViewModel() {
    private val _product = MutableLiveData<List<ShoppingProduct>>()
    val product: LiveData<List<ShoppingProduct>> = _product

    private val _removedProduct = MutableLiveData<ShoppingProduct>()
    val removedProduct: LiveData<ShoppingProduct> = _removedProduct

    private var _page = MutableLiveData(FIRST_PAGE_NUMBER)
    val page: LiveData<Int> = _page

    private val _hasNext = MutableLiveData(false)
    val hasNext: LiveData<Boolean> = _hasNext

    init {
        loadPage(FIRST_PAGE_NUMBER)
    }

    fun loadNextProducts() {
        _page.value = _page.value?.plus(1)
        loadPage(_page.value ?: FIRST_PAGE_NUMBER)
    }

    fun loadPreviousProducts() {
        _page.value = _page.value?.minus(1)
        loadPage(_page.value ?: FIRST_PAGE_NUMBER)
    }

    private fun loadPage(page: Int) {
        val result = repository.getPagedProducts(PAGE_SIZE, (page - 1) * PAGE_SIZE)
        _hasNext.value = result.hasNext
        _product.value = result.items
    }

    fun deleteProduct(shoppingProduct: ShoppingProduct) {
        repository.delete(shoppingProduct.id)
        _removedProduct.value = shoppingProduct
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

        private const val FIRST_PAGE_NUMBER = 1
        private const val PAGE_SIZE = 5
    }
}
