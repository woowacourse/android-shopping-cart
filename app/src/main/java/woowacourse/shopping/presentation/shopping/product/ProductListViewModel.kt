package woowacourse.shopping.presentation.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.repository.ShoppingRepository
import woowacourse.shopping.presentation.base.BaseViewModelFactory
import woowacourse.shopping.presentation.shopping.toShoppingUiModel

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ShoppingUiModel>>(emptyList())
    val products: LiveData<List<ShoppingUiModel>> = _products

    fun loadProducts() {
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        val currentProductIds = currentProducts.map { it.id }
        val newProducts =
            shoppingRepository.products(exceptProducts = currentProductIds)
                .map { it.toShoppingUiModel() }

        _products.value = currentProducts + newProducts
        if (shoppingRepository.canLoadMoreProducts(exceptProducts = currentProductIds)) {
            _products.value = _products.value?.plus(ShoppingUiModel.LoadMore)
        }
    }

    companion object {
        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(repository) }
        }
    }
}
