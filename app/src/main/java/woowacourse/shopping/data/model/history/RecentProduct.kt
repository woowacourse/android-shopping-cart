package woowacourse.shopping.data.model.history

import androidx.room.Embedded
import androidx.room.Relation
import woowacourse.shopping.data.database.history.HistoryContract
import woowacourse.shopping.data.database.product.ProductContract
import woowacourse.shopping.data.model.product.Product

data class RecentProduct(
    @Embedded val productHistory: ProductHistory,
    @Relation(
        parentColumn = HistoryContract.COLUMN_PRODUCT_ID,
        entityColumn = ProductContract.COLUMN_ID,
    )
    val product: Product,
)
