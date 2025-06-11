package woowacourse.shopping.data.product.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.product.CartItem

@Entity(tableName = "shoppingCart")
class CartItemEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
) {
    fun toDomain(): CartItem =
        CartItem(
            id,
            name,
            price,
            quantity,
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartItemEntity

        if (id != other.id) return false
        if (price != other.price) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + price
        result = 31 * result + name.hashCode()
        return result
    }

    companion object {
        fun CartItem.toEntity(): CartItemEntity =
            CartItemEntity(
                id,
                name,
                price,
                quantity,
            )
    }
}
