package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.domain.BasketProduct

fun DataBasketProduct.toDomain(): BasketProduct =
    BasketProduct(id = id, count = count.toDomain(), product = product.toDomain())

fun BasketProduct.toData(): DataBasketProduct =
    DataBasketProduct(id = id, count = count.toData(), product = product.toData())
