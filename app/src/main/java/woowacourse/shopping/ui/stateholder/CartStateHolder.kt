package woowacourse.shopping.ui.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.MockCatalog
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProducts
import woowacourse.shopping.domain.Product
import java.util.UUID
import kotlin.math.min

class CartStateHolder(
    val ids: List<String>,
) {
    var cart by mutableStateOf(createCart())

    var currentPage by mutableIntStateOf(0)

    private fun createCart(): Cart {
        val items = ids.map { it ->
            val id = UUID.fromString(it.trim())
            MockCatalog.findProductById(id)
        }
        return Cart(CartProducts(items))
    }
    fun onPrevious() {
        if (checkPreviousAvailable()) currentPage--
    }

    fun onNext() {
        if (checkNextAvailable()) currentPage++
    }

    fun onDeleteProduct(id: UUID) {
        cart = cart.removeProduct(id)
        if(cart.size() % 5 == 0) currentPage--
    }

    fun checkPreviousAvailable(): Boolean = currentPage > 0

    fun checkNextAvailable(): Boolean = currentPage < (cart.size() - 1) / 5

    fun isPageable(): Boolean = cart.size() > 5

    fun getPartedItem(
        page: Int,
        pageSize: Int = 5,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cart.size())
        return cart.cartProducts.items.subList(fromIndex, toIndex)
    }
}
