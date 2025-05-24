package woowacourse.shopping.view.loader

import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.main.state.ProductState

class ProductLoader(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
) {
    operator fun invoke(
        pageIndex: Int,
        pageSize: Int,
        onResult: (List<ProductState>, hasNextPage: Boolean) -> Unit,
    ) {
        productRepository.loadSinglePage(pageIndex, pageSize) { page ->
            val productIds = page.products.map { it.id }

            cartRepository.getCarts(productIds) { carts ->
                val productStates =
                    page.products.mapIndexed { index, product ->
                        val quantity = carts.getOrNull(index)?.quantity ?: Quantity(0)
                        ProductState(product, quantity)
                    }

                onResult(productStates, page.hasNextPage)
            }
        }
    }
}
