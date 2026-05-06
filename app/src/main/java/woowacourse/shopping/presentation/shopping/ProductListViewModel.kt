package woowacourse.shopping.presentation.shopping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
) : ViewModel() {
    var currentPageIndex by mutableStateOf(0)

    var products by mutableStateOf(Products())
        private set

    val hasNextPage: Boolean
        get() =
            productRepository.hasNextPage(
                currentPage = currentPageIndex,
                pageSize = pageSize,
            )

    init {
        loadPages(currentPageIndex)
    }

    fun loadMore() {
        if (!hasNextPage) return

        currentPageIndex++

        val nextProducts =
            productRepository.getPagingProducts(
                page = currentPageIndex,
                pageSize = pageSize,
            )

        products += nextProducts
    }

    private fun loadPages(currentPageIndex: Int) {
        products = Products()

        for (page in 0..currentPageIndex) {
            products +=
                productRepository.getPagingProducts(
                    page = page,
                    pageSize = pageSize,
                )
        }
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}

class ProductListViewModelFactory(
    private val productRepository: ProductRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(
                productRepository = productRepository,
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
