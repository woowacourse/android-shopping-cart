package woowacourse.shopping.ui.shopping.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import woowacourse.shopping.domain.PageRequest
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.SHOPPING_PAGE_SIZE
import woowacourse.shopping.domain.toPage

class ProductListState {
    var currentPageIndex by mutableStateOf(0)
        private set

    fun visibleProducts(products: Products): List<Product> =
        products.products
            .toPage(PageRequest(0, (currentPageIndex + 1) * SHOPPING_PAGE_SIZE))
            .items

    fun increase() {
        currentPageIndex++
    }

    fun decrease() {
        currentPageIndex--
    }

    companion object {
        val Saver =
            Saver<ProductListState, Int>(
                save = { it.currentPageIndex },
                restore = { ProductListState() },
            )
    }
}

@Composable
fun rememberProductListState(): ProductListState =
    rememberSaveable(saver = ProductListState.Saver) {
        ProductListState()
    }
