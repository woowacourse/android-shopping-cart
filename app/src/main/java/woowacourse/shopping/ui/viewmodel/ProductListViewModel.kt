package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.product.ProductRepository
import woowacourse.shopping.ui.productlist.ProductListViewType

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<ProductListViewType>>(emptyList())
    val products: LiveData<List<ProductListViewType>> get() = _products
    private var pageNumber = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val originProducts = _products.value.orEmpty().filterIsInstance<ProductListViewType.ProductItemType>()
        val newProducts = productRepository.productsByPageNumberAndSize(pageNumber++, 20).map { ProductListViewType.ProductItemType(it) }

        if (productRepository.canMoreLoad(pageNumber, 20)) {
            _products.value = originProducts + newProducts + ProductListViewType.LoadMoreType
        } else {
            _products.value = originProducts + newProducts
        }
    }
}
