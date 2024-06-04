package woowacourse.shopping.data.model.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import woowacourse.shopping.data.database.history.HistoryContract
import woowacourse.shopping.data.database.product.ProductContract
import woowacourse.shopping.data.model.product.Product

@Entity(
    tableName = HistoryContract.TABLE_PRODUCT_HISTORY,
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = [ProductContract.COLUMN_ID],
            childColumns = [HistoryContract.COLUMN_PRODUCT_ID],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ProductHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = HistoryContract.COLUMN_ID) val id: Long = 0,
    @ColumnInfo(name = HistoryContract.COLUMN_PRODUCT_ID) val productId: Long,
)
