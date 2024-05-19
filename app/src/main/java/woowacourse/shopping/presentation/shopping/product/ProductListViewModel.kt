package woowacourse.shopping.presentation.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.shopping.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.toShoppingUiModel

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ShoppingUiModel>>(emptyList())
    val products: LiveData<List<ShoppingUiModel>> = _products
    var currentPage = 0

    fun loadProducts() {
        val loadProducts =
            shoppingRepository.products(currentPage++, PRODUCT_AMOUNT)
                .map { it.toShoppingUiModel() }

        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = loadProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = loadProducts
        }
    }

    companion object {
        private const val PRODUCT_AMOUNT: Int = 20

        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(repository) }
        }
    }
}
