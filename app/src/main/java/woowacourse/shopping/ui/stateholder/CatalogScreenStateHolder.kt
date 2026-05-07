package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import kotlinx.coroutines.runBlocking
import woowacourse.shopping.MockCatalog
import woowacourse.shopping.domain.Product

class CatalogScreenStateHolder(
    val mockingCatalog: MockCatalog,
) {
    private val _catalog = mutableStateOf(emptyList<Product>())
    val catalog: List<Product> get() = _catalog.value

    private var recentItemIndex = 0

    init {
        loadProducts()
    }

    fun onLoadClick() {
        recentItemIndex++
        loadProducts()
    }

    private fun loadProducts() {
        val products = runBlocking {
            mockingCatalog.loadProducts(recentItemIndex, MAX_PRODUCT).await()
        }
        _catalog.value += products
    }

    companion object {
        const val MAX_PRODUCT = 20
    }
}

@Composable
fun retainCartStateHolder(): CatalogScreenStateHolder =
    retain {
        CatalogScreenStateHolder(MockCatalog)
    }
