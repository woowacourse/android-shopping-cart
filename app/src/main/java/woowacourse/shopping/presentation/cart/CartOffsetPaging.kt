package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.Page
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.util.OffsetPaging

class CartOffsetPaging(
    private val cartRepository: CartRepository,
) : OffsetPaging<CartProductInfo>() {

    override val limit: Int = LIMIT
    override fun plusPage(): Page {
        currentPage += PAGE_STEP
        return currentPage
    }

    override fun minusPage(): Page {
        currentPage -= PAGE_STEP
        return currentPage
    }

    override fun getPageItems(page: Page): List<CartProductInfo> {
        return cartRepository.getCartProductsInfo(limit, page.getOffset(limit)).items
    }

    override fun isPlusPageAble(): Boolean = getPageItems(currentPage + 1).isNotEmpty()

    companion object {
        private const val PAGE_STEP = 1
        private const val LIMIT = 5
    }
}
