package woowacourse.shopping.ui.cart.stateholder

import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import woowacourse.shopping.ui.state.ProductUiModel

class CartStateHolder(initialItems: List<ProductUiModel>, initialPage: Int = 1) {
    var totalCartItems: List<ProductUiModel> = initialItems
    var page by mutableIntStateOf(initialPage)
    var cartItems by mutableStateOf(pagination(initialPage, initialItems))
    fun isStartPage(): Boolean {
        return page == 1
    }

    fun isEndPage(): Boolean = page >= lastPage()

    private fun lastPage(): Int {
        if (totalCartItems.isEmpty()) return 1
        return (totalCartItems.size + PAGE_SIZE - 1) / PAGE_SIZE
    }

    fun moveToPreviousPage() {
        page -= 1
        cartItems = pagination(page, totalCartItems)
    }

    fun moveToNextPage() {
        page += 1
        cartItems = pagination(page, totalCartItems)
    }

    private fun pagination(
        page: Int,
        productUiModels: List<ProductUiModel>,
    ): List<ProductUiModel> {
        val toIndex = minOf(page * PAGE_SIZE, productUiModels.size)
        return productUiModels.subList((page - 1) * PAGE_SIZE, toIndex)
    }

    fun deleteCartItem(id: String) {
        totalCartItems = totalCartItems.filter { it.id != id }
        cartItems = pagination(page, totalCartItems)
    }

    companion object {
        private const val PAGE_SIZE = 5
        private const val KEY_PAGE = "page"
        private const val KEY_ITEMS = "items"

        val Saver: Saver<CartStateHolder, Bundle> = Saver(
            save = { holder ->
                Bundle().apply {
                    putInt(KEY_PAGE, holder.page)
                    putParcelableArrayList(KEY_ITEMS, ArrayList(holder.totalCartItems))
                }
            },
            restore = { bundle ->
                val items: List<ProductUiModel> =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        bundle.getParcelableArrayList(KEY_ITEMS, ProductUiModel::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        bundle.getParcelableArrayList(KEY_ITEMS)
                    }
                        ?: emptyList()
                val page = bundle.getInt(KEY_PAGE, 1)
                CartStateHolder(items, page)
            },
        )
    }
}
