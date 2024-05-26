package woowacourse.shopping.data.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity

@Entity(tableName = "cart")
data class CartItemEntity(
    @ColumnInfo(name = "product") val product: Product,
    @ColumnInfo(name = "quantity") val quantity: Quantity,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L

    companion object {
        fun CartItemEntity.toDomainModel() =
            CartItem(
                product = product,
                quantity = quantity,
            )
    }
}
//
// @Entity(tableName = "inquiry_history")
// data class InquiryHistoryEntity(
//    @ColumnInfo(name = "product") val product: Product,
//    @ColumnInfo(name = "inquiry_time") val inquiryTime: LocalDateTime,
// ) {
//    @PrimaryKey(autoGenerate = true)
//    var uid: Long = 0L
//
//    companion object {
//        fun InquiryHistoryEntity.toDomainModel() =
//            InquiryHistory(
//                product = product,
//                inquiryTime = inquiryTime,
//            )
//    }
// }
