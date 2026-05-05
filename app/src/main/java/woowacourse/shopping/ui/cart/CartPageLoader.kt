package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.model.CartItemUiModel
import kotlin.collections.lastIndex
import kotlin.math.min

class CartPageLoader(
    private val pageSize: Int = 5,
) {
    fun getCartPage(
        page: Int = 0,
        items: List<CartItemUiModel>
    ) : CartPage{
        val lastPage =
            if (items.isEmpty()) {
                0
            } else {
                items.lastIndex / pageSize
            }

        val currentPage = page.coerceIn(0, lastPage)
        val fromIndex = currentPage * pageSize
        val toIndex = min(fromIndex + pageSize, items.size)

        return CartPage(
            items = items.subList(fromIndex, toIndex),
            isCanMoveNext = toIndex < items.size,
            page = currentPage
        )
    }
}

data class CartPage(
    val items : List<CartItemUiModel>,
    val isCanMoveNext : Boolean,
    val page : Int
)