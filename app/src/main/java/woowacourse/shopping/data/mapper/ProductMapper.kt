package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataProduct
import woowacourse.shopping.domain.Product

fun DataProduct.toDomain(): Product =
    Product(id = id, name = name, price = price.toDomain(), imageUrl = imageUrl)

fun Product.toData(): DataProduct =
    DataProduct(id = id, name = name, price = price.toData(), imageUrl = imageUrl)
