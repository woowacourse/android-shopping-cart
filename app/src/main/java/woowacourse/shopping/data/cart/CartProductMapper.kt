package woowacourse.shopping.data.cart

import woowacourse.shopping.data.product.toId
import woowacourse.shopping.data.product.toProduct
import woowacourse.shopping.domain.model.CartProduct

fun CartProductEntity.toDomain() =
    CartProduct(
        id = this.id,
        product = this.productId.toProduct(),
        quantity = this.quantity,
    )

fun CartProduct.toEntity() =
    CartProductEntity(
        productId = this.product.toId(),
        quantity = this.quantity,
    )
