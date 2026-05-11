package woowacourse.shopping.data.db

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.entity.CatalogEntity
import woowacourse.shopping.data.entity.RecentProductEntity

data class RecentProductWithProduct(
    @Embedded val recentProductEntity: RecentProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val catalogEntity: CatalogEntity
)
