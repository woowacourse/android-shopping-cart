package woowacourse.shopping.presentation.shopping.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.data.ShoppingRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.base.BaseViewModelFactory

class ProductListViewModel(
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductUi>>(emptyList())
    val products: LiveData<List<ProductUi>> get() = _products

    init {
        loadProducts()
    }

    fun loadProducts() {
        _products.value = shoppingRepository.products(PRODUCT_AMOUNT).map { it.toUiModel() }
    }

    companion object {
        private const val PRODUCT_AMOUNT = 10

        fun factory(repository: ShoppingRepository): ViewModelProvider.Factory {
            return BaseViewModelFactory { ProductListViewModel(repository) }
        }
    }
}

fun Product.toUiModel(): ProductUi {
    return ProductUi(id, name, price, imageUrl)
}
