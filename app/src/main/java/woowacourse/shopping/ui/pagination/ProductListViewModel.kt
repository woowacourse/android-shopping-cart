package woowacourse.shopping.ui.pagination

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.model.Product
import woowacourse.shopping.repository.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private var currentPage: Int = 0
    var products = mutableStateListOf<Product>()
        private set

    val showMoreButton: Boolean
        get() = productRepository.totalSize > products.size

    init {
        loadMoreProducts()
    }

    fun loadMoreProducts() {
        productRepository
            .getProducts(
                page = currentPage++,
                pageSize = 20,
            ).forEach {
                products.add(it)
            }
    }

    companion object {
        val Factory =
            viewModelFactory {
                initializer {
                    ProductListViewModel(
                        productRepository = ShoppingApplication.productRepository,
                    )
                }
            }
    }
}
