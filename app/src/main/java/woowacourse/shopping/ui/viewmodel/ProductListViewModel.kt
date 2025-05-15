package woowacourse.shopping.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository
): ViewModel() {
    private val _products = MutableLiveData<List<Product>>(emptyList())
    val products: LiveData<List<Product>> get() = _products
    private var pageNumber = 0

    init {
        loadProducts()
    }

    fun loadProducts() {
        val originProducts = _products.value.orEmpty()
        val newProducts = productRepository.productsByPageNumberAndSize(pageNumber++, 20)

        if (productRepository.canMoreLoad(pageNumber, 20)) {
            _products.value = originProducts + newProducts
            // 더보기 버튼 처리
        } else {
            _products.value = originProducts + newProducts
        }
    }


}
