package woowacourse.shopping.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.model.ShoppingGoods

@Entity(tableName = "shoppingCart")
data class ShoppingEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val quantity: Int,
)

fun ShoppingEntity.toShoppingGoods() =
    ShoppingGoods(
        goodsId = id,
        goodsQuantity = quantity,
    )

fun ShoppingGoods.toShoppingEntity() =
    ShoppingEntity(
        id = goodsId,
        quantity = goodsQuantity,
    )
