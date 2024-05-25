package woowacourse.shopping.data.model.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import woowacourse.shopping.data.database.cart.CartContract
import woowacourse.shopping.data.database.product.ProductContract
import woowacourse.shopping.data.model.product.Product

@Entity(
    tableName = CartContract.TABLE_CART_ITEM,
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = [ProductContract.COLUMN_ID],
            childColumns = [CartContract.COLUMN_PRODUCT_ID],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CartContract.COLUMN_ID) val id: Long? = null,
    @ColumnInfo(name = CartContract.COLUMN_PRODUCT_ID) val productId: Long,
    @ColumnInfo(name = CartContract.COLUMN_QUANTITY) val quantity: Int = 1,
)
