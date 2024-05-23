package woowacourse.shopping.data.shopping.recent

import woowacourse.shopping.domain.entity.Product
import woowacourse.shopping.local.entity.RecentProductEntity
import java.time.LocalDateTime


fun RecentProductEntity.toData(): RecentProductData {
    return RecentProductData(productId, createdTime)
}

fun RecentProductData.toEntity(): RecentProductEntity {
    return RecentProductEntity(productId, createdTime)
}