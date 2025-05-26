package woowacourse.shopping.data.model.response

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.model.entity.HistoryProductEntity
import woowacourse.shopping.data.model.entity.ProductEntity

data class HistoryProductResponse(
    @Embedded val historyProduct: HistoryProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id",
    )
    val product: ProductEntity?,
)
