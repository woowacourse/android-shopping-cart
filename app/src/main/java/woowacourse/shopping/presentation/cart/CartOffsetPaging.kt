package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.Page
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.util.OffsetPaging

class CartOffsetPaging(
    override val limit: Int = LIMIT,
    private val startPage: Int = START_PAGE,
    private val cartRepository: CartRepository,
) : OffsetPaging<CartProductInfo>(startPage) {
    fun plusPage() {
        setPage(currentPage.value.plus(PAGE_STEP))
    }

    fun minusPage() {
        setPage(currentPage.value.minus(PAGE_STEP))
    }

    override fun loadPageItems(page: Page): List<CartProductInfo> {
        return cartRepository.getCartProductsInfo(limit, page.getOffset(limit)).items
    }

    override fun isPlusPageAble(): Boolean {
        val page = currentPage.value.plus(PAGE_STEP)
        return loadPageItems(page).isNotEmpty()
    }

    override fun isMinusPageAble(): Boolean {
        val page = currentPage.value
        return page.value != startPage
    }

    companion object {
        private const val START_PAGE = 1
        private const val PAGE_STEP = 1
        private const val LIMIT = 5
    }
}
