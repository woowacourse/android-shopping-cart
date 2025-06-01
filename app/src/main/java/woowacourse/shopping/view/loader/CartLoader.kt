package woowacourse.shopping.view.loader

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.view.main.state.ProductState

class CartLoader(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) {
    operator fun invoke(
        pageIndex: Int,
        pageSize: Int,
        onResult: (List<ProductState>, hasNextPage: Boolean) -> Unit,
    ) {
        cartRepository.singlePage(pageIndex, pageSize) { page ->
            if (page.carts.isEmpty()) {
                onResult(emptyList(), page.hasNextPage)
                return@singlePage
            }

            loadProductsForCarts(page.carts) { productStates ->
                onResult(productStates, page.hasNextPage)
            }
        }
    }

    private fun loadProductsForCarts(
        carts: List<Cart>,
        onComplete: (List<ProductState>) -> Unit,
    ) {
        val productIds = carts.map { it.productId }

        productRepository.getProducts(productIds) { products ->
            onComplete(mapToProductStates(carts, products))
        }
    }

    private fun mapToProductStates(
        carts: List<Cart>,
        products: List<Product>,
    ): List<ProductState> {
        return carts.mapNotNull { cart ->
            products.find { it.id == cart.productId }?.let { product ->
                ProductState(product, cart.quantity)
            }
        }
    }
}
