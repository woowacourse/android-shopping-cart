package woowacourse.shopping.presentation.cart

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.Page
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.util.OffsetPaging

class CartOffsetPaging(
    override val limit: Int = LIMIT,
    startPage: Int = START_PAGE,
    private val cartRepository: CartRepository,
) : OffsetPaging<CartProductInfo>(startPage) {
    fun plusPage() {
        setPage(currentPage.plus(PAGE_STEP))
    }

    fun minusPage() {
        setPage(currentPage.minus(PAGE_STEP))
    }

    private fun loadPageItems(page: Page, onSuccess: (List<CartProductInfo>) -> Unit) {
        cartRepository.getCartProductsInfo(limit, page.getOffset(limit)) {
            onSuccess(it)
        }
    }

    fun isPlusPageAble(onSuccess: (Boolean) -> Unit) {
        val page = currentPage.plus(PAGE_STEP)
        loadPageItems(page) {
            onSuccess(it.isNotEmpty())
        }
    }

    fun isMinusPageAble(): Boolean {
        val page = currentPage.value
        return page != MINIMUM_PAGE
    }

    companion object {
        private const val MINIMUM_PAGE = 1
        private const val START_PAGE = 1
        private const val PAGE_STEP = 1
        private const val LIMIT = 5
    }
}
