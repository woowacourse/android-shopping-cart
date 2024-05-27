package woowacourse.shopping.presentation.ui.productlist.adapter

import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.productlist.PagingProduct

class ProductListPagingSource(
    private val productRepository: ProductRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    private var currentPage = INIT_PAGE_NUM
    private var last = false

    fun load(): Result<PagingProduct> {
        if (last) return Result.failure(NoSuchElementException())

        val result =
            productRepository.getPagingProduct(page = currentPage, pageSize = PAGING_SIZE)
                .mapCatching { pagingProduct ->
                    val cartProducts = shoppingCartRepository.getAllCartProducts().getOrThrow()

                    pagingProduct.map { product ->
                        val findProduct = cartProducts.find { it.id == product.id }
                        if (findProduct == null) {
                            product
                        } else {
                            product.copy(quantity = findProduct.quantity)
                        }
                    }
                }

        return result.fold(
            onSuccess = { products ->
                if (products.size < PAGING_SIZE) last = true
                currentPage++
                Result.success(PagingProduct(products, last))
            },
            onFailure = { e ->
                Result.failure(e)
            },
        )
    }

    companion object {
        private const val PAGING_SIZE = 20
        private const val INIT_PAGE_NUM = 0
    }
}
