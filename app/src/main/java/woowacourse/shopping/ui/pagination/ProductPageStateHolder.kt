package woowacourse.shopping.ui.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import woowacourse.shopping.model.Product
import kotlin.math.min

class ProductPageStateHolder(
    products: List<Product>,
    private val pageSize: Int,
    initialPage: Int = 0,
) {
    private val allProducts: List<Product> = products

    var currentPage: Int by mutableIntStateOf(initialPage)
        private set

    private val pageCount: Int
        get() {
            val totalPageCount = allProducts.size / pageSize
            return if (allProducts.size % pageSize == 0) {
                totalPageCount
            } else {
                totalPageCount + 1
            }
        }

    fun getItems(): List<Product> {
        val endItemIndex = min((currentPage + 1) * pageSize, allProducts.size)
        return allProducts.subList(0, endItemIndex)
    }

    fun nextPage() {
        if (canMoveToNextPage()) {
            currentPage++
        }
    }

    fun canMoveToNextPage(): Boolean = currentPage + 1 < pageCount

    companion object {
        fun Saver(
            products: List<Product>,
            pageSize: Int,
        ): Saver<ProductPageStateHolder, Int> =
            Saver(
                save = { it.currentPage },
                restore = { ProductPageStateHolder(products, pageSize, it) },
            )
    }
}
