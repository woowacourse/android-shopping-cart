package woowacourse.shopping.presentation.view.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.model.toUiModel

class CatalogViewModel(
    private val dummyProducts: List<Product> = emptyList(),
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductUiModel>>()
    val products: LiveData<List<ProductUiModel>> = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        val newProducts = dummyProducts.map { it.toUiModel() }

        _products.postValue(
            (_products.value ?: emptyList())
                .plus(newProducts)
                .distinct(),
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T = CatalogViewModel(dummyProducts) as T
            }
    }
}
