package woowacourse.shopping.presentation.ui.productlist.adapter

import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.presentation.ui.productlist.PagingProduct

class ProductListPagingSource(private val repository: ProductRepository) {
    private var currentPage = INIT_PAGE_NUM
    private var last = false

    fun load(): Result<PagingProduct> {
        if (last) return Result.failure(NoSuchElementException())

        val result = repository.getPagingProduct(page = currentPage, pageSize = PAGING_SIZE)

        return result.fold(
            onSuccess = { products ->
                if (products.size < PAGING_SIZE) last = true
                currentPage++
                Result.success(PagingProduct(products, last))
            },
            onFailure = { e ->
                last = true
                Result.failure(e)
            },
        )
    }

    companion object {
        private const val PAGING_SIZE = 20
        private const val INIT_PAGE_NUM = 0
    }
}
