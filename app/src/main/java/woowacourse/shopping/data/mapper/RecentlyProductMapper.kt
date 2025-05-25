package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.recentlyproducts.RecentlyProductsEntity
import woowacourse.shopping.domain.Product

fun Product.toRecentEntity() =
    RecentlyProductsEntity(
        productId = this.id,
    )
