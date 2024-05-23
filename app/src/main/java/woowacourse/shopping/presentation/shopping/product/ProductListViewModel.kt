package woowacourse.shopping.presentation.shopping.product

import android.util.Log
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
    private var currentPage = 0

    fun loadProducts() {
        val currentProducts = _products.value.orEmpty().filterIsInstance<ShoppingUiModel.Product>()
        val loadProducts =
            shoppingRepository.products(currentPage++, PRODUCT_AMOUNT)
                .map { it.toShoppingUiModel() }

        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = currentProducts + loadProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = currentProducts + loadProducts
        }
    }

    fun increaseCount(id: Long) {
        val newProducts = _products.value?.filterIsInstance<ShoppingUiModel.Product>()?.map {
            if (it.id == id) it.copy(count = it.count + 1)
            else it
        } ?: return
        if (shoppingRepository.canLoadMoreProducts(currentPage, PRODUCT_AMOUNT)) {
            _products.value = newProducts + ShoppingUiModel.LoadMore
        } else {
            _products.value = newProducts
        }
    }

    companion object {
        private const val PRODUCT_AMOUNT: Int = 20

        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(repository) }
        }
    }
}
