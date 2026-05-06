package woowacourse.shopping.presentation.shopping

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository

class ProductListStateHolder(
    private val productRepository: ProductRepository,
    private val pageSize: Int = DEFAULT_PAGE_SIZE,
    initialPageIndex: Int = 0,
    private val onPageIndexChanged: (Int) -> Unit,
) {
    private var currentPageIndex = initialPageIndex

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
        onPageIndexChanged(currentPageIndex)

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
