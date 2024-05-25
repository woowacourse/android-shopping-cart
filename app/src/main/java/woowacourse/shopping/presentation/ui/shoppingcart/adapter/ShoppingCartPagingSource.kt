package woowacourse.shopping.presentation.ui.shoppingcart.adapter

import woowacourse.shopping.domain.repository.ShoppingCartRepository
import woowacourse.shopping.presentation.ui.shoppingcart.PagingCartProduct

class ShoppingCartPagingSource(private val repository: ShoppingCartRepository) {
    private var maxOffset = 0
    private var last = true

    fun load(page: Int): Result<PagingCartProduct> {
        val result = repository.getCartProductsPaged(page = page, pageSize = PAGING_SIZE)
        maxOffset = repository.getCartProductsTotal().getOrThrow()

        return result.fold(
            onSuccess = { cardProducts ->
                last = maxOffset <= (PAGING_SIZE * (page + 1))
                Result.success(PagingCartProduct(cardProducts, page, last))
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
