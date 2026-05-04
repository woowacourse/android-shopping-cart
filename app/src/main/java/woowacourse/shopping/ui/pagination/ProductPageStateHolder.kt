package woowacourse.shopping.ui.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.model.Product
import kotlin.math.min

class ProductPageStateHolder(
    products: List<Product>,
) {
    private val pageSize: Int = 20
    private val allProducts: List<Product> = products

    var currentPage: Int by mutableIntStateOf(0)
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
}
