package woowacourse.shopping.storage.room.shoppingcart

import androidx.room.ColumnInfo
import woowacourse.shopping.model.Price
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductTitle
import woowacourse.shopping.model.ShoppingCartItem
import woowacourse.shopping.model.ShoppingItem

data class ShoppingCartItemRow(
    @ColumnInfo(name = "id")
    val id: Long,
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

fun ShoppingCartItemRow.toDomain(): ShoppingCartItem =
    ShoppingCartItem(
        id = id,
        shoppingItem =
            ShoppingItem(
                product =
                    Product(
                        id = productId,
                        title = ProductTitle(title),
                        price = Price(price),
                        imageUrl = imageUrl,
                    ),
                quantity = quantity,
            ),
    )
