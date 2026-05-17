package woowacourse.shopping.ui.model

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

data class UiProduct(
    val id: String,
    val name: String,
    val price: Price,
    val imageUrl: String,
    val cartQuantity: Int,
)

fun Product.toUiModel(cartQuantity: Int): UiProduct =
    UiProduct(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        cartQuantity = cartQuantity,
    )
