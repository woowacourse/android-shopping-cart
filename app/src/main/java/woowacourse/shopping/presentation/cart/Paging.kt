package woowacourse.shopping.presentation.cart

import woowacourse.shopping.Counter
import woowacourse.shopping.Product
import woowacourse.shopping.Products
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository

class Paging(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) {
    var page = Counter(START_PAGE)

    fun plusPage(): Counter {
        page += PAGE_STEP
        return page
    }

    fun minusPage(): Counter {
        page -= PAGE_STEP
        return page
    }

    fun getCurrentPageProducts(): Products {
        val cartProductIds = cartRepository.getCartProductIds(
            limit = PAGE_PER_PRODUCT_COUNT,
            offset = (page.value - START_PAGE) * PAGE_PER_PRODUCT_COUNT,
        )
        return Products(
            cartProductIds.map { productRepository.findProductById(it) ?: Product.defaultProduct },
        )
    }

    private fun getNextPageProducts(): Products {
        val cartProductIds = cartRepository.getCartProductIds(
            limit = PAGE_PER_PRODUCT_COUNT,
            offset = (page.value) * PAGE_PER_PRODUCT_COUNT,
        )
        return Products(
            cartProductIds.map { productRepository.findProductById(it) ?: Product.defaultProduct },
        )
    }

    fun isPlusPageAble(): Boolean =
        getNextPageProducts().items.isNotEmpty()

    fun isMinusPageAble(): Boolean = page.value != START_PAGE

    companion object {
        private const val START_PAGE = 1
        private const val PAGE_STEP = 1
        private const val PAGE_PER_PRODUCT_COUNT = 5
    }
}
