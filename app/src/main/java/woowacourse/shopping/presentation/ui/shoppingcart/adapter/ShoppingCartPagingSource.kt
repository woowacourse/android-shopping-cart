package woowacourse.shopping.presentation.ui.shoppingcart.adapter

import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.shoppingcart.PagingOrder

class ShoppingCartPagingSource(private val repository: ShoppingCartRepository) {
    fun load(page: Int): Result<PagingOrder> {
        val result = repository.getPagingOrder(page = page, pageSize = PAGING_SIZE)

        return result.fold(
            onSuccess = { pagingOrder ->
                val last = pagingOrder.maxOffSet <= (PAGING_SIZE * (page + 1))
                Result.success(PagingOrder(pagingOrder.orders, page, last))
            },
            onFailure = { e ->
                Result.failure(e)
            },
        )
    }

    companion object {
        private const val PAGING_SIZE = 5
    }
}
