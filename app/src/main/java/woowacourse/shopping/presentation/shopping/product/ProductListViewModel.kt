package woowacourse.shopping.presentation.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.toShoppingUiModel

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ShoppingUiModel>>(emptyList())
    val products: LiveData<List<ShoppingUiModel>> = _products
    private var currentPage = 1

    init {
        loadProducts()
    }

    fun loadProducts() {
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        // TODO : 현재 Executor cancel 처리가 되지 않음
        shoppingRepository.products(currentPage, PAGE_SIZE)
            .onSuccess {
                val newProducts = it.map(Product::toShoppingUiModel)
                _products.value = currentProducts + newProducts + getLoadMore(++currentPage)
            }.onFailure {
                // TODO : Handle error
            }
    }

    private fun getLoadMore(page: Int): List<ShoppingUiModel.LoadMore> {
        return if (shoppingRepository.canLoadMore(page, PAGE_SIZE).getOrNull() == true) {
            listOf(ShoppingUiModel.LoadMore)
        } else {
            emptyList()
        }
    }

    companion object {
        private const val PAGE_SIZE = 20

        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(repository) }
        }
    }
}
