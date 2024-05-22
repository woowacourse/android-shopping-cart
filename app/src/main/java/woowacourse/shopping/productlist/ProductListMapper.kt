package woowacourse.shopping.productlist

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCartItem

fun Product.toProductUiModel(cartItemCount: Int): ProductUiModel =
    ProductUiModel(
        id,
        name,
        price.value,
        imageUrl.url,
        cartItemCount,
    )

fun ShoppingCartItem.toProductUiModel(): ProductUiModel =
    ProductUiModel(
        this.product.id,
        this.product.name,
        this.product.price.value,
        this.product.imageUrl.url,
        this.totalQuantity,
    )
