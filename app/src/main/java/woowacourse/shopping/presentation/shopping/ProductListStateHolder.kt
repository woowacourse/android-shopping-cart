package woowacourse.shopping.presentation.shopping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository

class ProductListStateHolder(
    private val productRepository: ProductRepository,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
) {
    private var currentPageIndex = 0

    var products by mutableStateOf(Products())
        private set
    var hasNextPage by mutableStateOf(false)
        private set

    init {
        loadInitialPage()
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
        updateHasNextPage()
    }

    private fun loadInitialPage() {
        products =
            productRepository.getPagingProducts(
                page = currentPageIndex,
                pageSize = pageSize,
            )
        updateHasNextPage()
    }

    private fun updateHasNextPage() {
        hasNextPage =
            productRepository.hasNextPage(
                currentPage = currentPageIndex,
                pageSize = pageSize,
            )
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
