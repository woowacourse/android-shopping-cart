package woowacourse.shopping.presentation.cart

import woowacourse.shopping.Page
import woowacourse.shopping.Product
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.util.OffsetPaging

class CartOffsetPaging(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : OffsetPaging<Product>() {

    override val limit: Int = LIMIT
    override fun plusPage(): Page {
        currentPage += PAGE_STEP
        return currentPage
    }

    override fun minusPage(): Page {
        currentPage -= PAGE_STEP
        return currentPage
    }

    override fun getPageItems(page: Page): List<Product> {
        val cartProductIds = cartRepository.getCartProductIds(limit, page.getOffset(limit))
        return cartProductIds.map {
            productRepository.findProductById(it) ?: Product.defaultProduct
        }
    }

    override fun isPlusPageAble(): Boolean = getPageItems(currentPage + 1).isNotEmpty()

    companion object {
        private const val PAGE_STEP = 1
        private const val LIMIT = 5
    }
}
