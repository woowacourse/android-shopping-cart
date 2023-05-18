package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.domain.BasketProduct

fun DataBasketProduct.toDomain(): BasketProduct = BasketProduct(
    id = id,
    product = product.toDomain(),
    selectedCount = selectedCount.toDomain(),
)

fun BasketProduct.toData(): DataBasketProduct = DataBasketProduct(
    id = id,
    product = product.toData(),
    selectedCount = selectedCount.toData(),
)
