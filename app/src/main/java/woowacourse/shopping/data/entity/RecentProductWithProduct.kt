package woowacourse.shopping.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecentProductWithProduct(
    @Embedded
    val recentProduct: RecentProductEntity,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id",
    )
    val product: ProductEntity?,
)
