package woowacourse.shopping.data.cart

import woowacourse.shopping.data.product.toProduct
import woowacourse.shopping.domain.model.CartProduct

fun CartProductEntity.toDomain() =
    CartProduct(
        product = this.productId.toProduct(),
        quantity = this.quantity,
    )

fun CartProduct.toEntity() =
    CartProductEntity(
        productId = this.product.id,
        quantity = this.quantity,
    )
