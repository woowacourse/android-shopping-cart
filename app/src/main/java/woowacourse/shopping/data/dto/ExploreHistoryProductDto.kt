package woowacourse.shopping.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.entity.ExploreHistoryProductEntity
import woowacourse.shopping.data.entity.ProductEntity

data class ExploreHistoryProductDto(
    @Embedded val exploreHistoryProduct: ExploreHistoryProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: ProductEntity,
)
