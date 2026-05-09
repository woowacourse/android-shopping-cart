package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct
import java.util.UUID

@Entity(tableName = "purchase_products")
data class PurchaseProductEntity(
    @PrimaryKey @ColumnInfo(name = "uuid") val id: UUID,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "imageUri") val imageUri: String,
    @ColumnInfo(name = "count") val count: Int
) {
    fun toPurchaseProductObject(): PurchaseProduct {
        return PurchaseProduct(
            product = Product(
                uuid = id,
                imageUri = imageUri,
                name = name,
                price = price
            ),
            count = count
        )
    }
}
