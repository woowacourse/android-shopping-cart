package woowacourse.shopping.storage.room.shoppingItem

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.ShoppingItem

@Entity(tableName = "shopping_items")
data class ShoppingItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val productId: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
)

fun ShoppingItemEntity.toDomain(): ShoppingItem =
    ShoppingItem(
        product =
            Product(
                id = productId,
                title = ProductTitle(title),
                price = Price(price),
                imageUrl = imageUrl,
            ),
        quantity = quantity,
    )
