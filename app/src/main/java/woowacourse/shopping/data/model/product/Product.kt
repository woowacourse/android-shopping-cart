package woowacourse.shopping.data.model.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.database.product.ProductContract

@Entity(tableName = ProductContract.TABLE_PRODUCT)
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProductContract.COLUMN_ID) val id: Long = 1,
    @ColumnInfo(name = ProductContract.COLUMN_NAME) val name: String,
    @ColumnInfo(name = ProductContract.COLUMN_IMAGE_SOURCE) val imageSource: String,
    @ColumnInfo(name = ProductContract.COLUMN_PRICE) val price: Int,
)
