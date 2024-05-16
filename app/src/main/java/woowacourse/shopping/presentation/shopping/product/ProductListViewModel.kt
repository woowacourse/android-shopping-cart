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

    init {
        loadProducts()
    }

    fun loadProducts() {
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        val currentProductIds = currentProducts.map { it.id }

        val newProducts = shoppingRepository.products(exceptProducts = currentProductIds)
            .map { it.toShoppingUiModel() }

        if (shoppingRepository.canLoadMoreProducts(currentProductIds)) {
            _products.value = currentProducts + newProducts + ShoppingUiModel.Plus
        } else {
            _products.value = currentProducts + newProducts
        }
    }

    companion object {
        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(repository) }
        }
    }
}
