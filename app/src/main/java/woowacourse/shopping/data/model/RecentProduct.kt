package woowacourse.shopping.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class RecentProduct(
    @Embedded val productHistory: ProductHistory,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: Product,
)
