package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataRecentProduct
import woowacourse.shopping.domain.RecentProduct

fun DataRecentProduct.toDomain(): RecentProduct =
    RecentProduct(id = id, product = product.toDomain())

fun RecentProduct.toData(): DataRecentProduct =
    DataRecentProduct(id = id, product = product.toData())
