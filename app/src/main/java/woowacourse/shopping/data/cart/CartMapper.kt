package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.model.CartData
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.local.entity.CartEntity

fun CartData.toEntity(product: Product): CartProduct {
    return CartProduct(product, count)
}

fun CartProduct.toEntity(): CartEntity {
    return CartEntity(product.id, count)
}

fun CartEntity.toData(): CartData {
    return CartData(id, count)
}
