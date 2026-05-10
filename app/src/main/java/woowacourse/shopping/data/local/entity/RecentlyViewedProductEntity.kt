package woowacourse.shopping.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.Product

@Entity(tableName = "recently_viewed_products")
data class RecentlyViewedProductEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "imageUri") val imageUri: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long = System.currentTimeMillis(),
) {
    fun toObject(): Product =
        Product(
            id = id,
            imageUri = imageUri,
            name = name,
            price = price,
        )
}
