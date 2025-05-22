package woowacourse.shopping.view.uimodel

import woowacourse.shopping.data.page.Page
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem

data class MainRecyclerViewProduct(
    val page: Page<Product>,
    val shoppingCartItems: List<ShoppingCartItem>,
)
