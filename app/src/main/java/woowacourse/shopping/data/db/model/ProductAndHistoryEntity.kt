package woowacourse.shopping.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
class ProductAndHistoryEntity(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
    )
    val history: ProductBrowsingHistoryEntity,
)
