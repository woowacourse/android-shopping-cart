package woowacourse.shopping.ui.cart.presenter

import woowacourse.shopping.repository.CartItemRepository
import woowacourse.shopping.ui.cart.PageableView

class ShowPageUI(
    private val view: PageableView,
    private val cartItemRepository: CartItemRepository,
    private val pageSize: Int
) {

    operator fun invoke(currentPage: Int) {
        refreshStateThatCanRequestPreviousPage(currentPage)
        refreshStateThatCanRequestNextPage(currentPage)
        view.setPage(currentPage)
    }

    private fun refreshStateThatCanRequestPreviousPage(currentPage: Int) {
        if (currentPage <= 1) {
            view.setStateThatCanRequestPreviousPage(false)
        } else {
            view.setStateThatCanRequestPreviousPage(true)
        }
    }

    private fun refreshStateThatCanRequestNextPage(currentPage: Int) {
        if (currentPage >= getMaxPage()) {
            view.setStateThatCanRequestNextPage(false)
        } else {
            view.setStateThatCanRequestNextPage(true)
        }
    }

    private fun getMaxPage(): Int = (cartItemRepository.countAll() - 1) / pageSize + 1
}