package woowacourse.shopping.data.shopping.recent

import woowacourse.shopping.local.entity.RecentProductEntity

fun RecentProductEntity.toData(): RecentProductData {
    return RecentProductData(productId, createdTime)
}

fun RecentProductData.toEntity(): RecentProductEntity {
    return RecentProductEntity(productId, createdTime)
}
