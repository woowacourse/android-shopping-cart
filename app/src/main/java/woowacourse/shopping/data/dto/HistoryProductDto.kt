package woowacourse.shopping.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.entity.HistoryProductEntity
import woowacourse.shopping.data.entity.ProductEntity

data class HistoryProductDto(
    @Embedded val exploreHistoryProduct: HistoryProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: ProductEntity?,
)
