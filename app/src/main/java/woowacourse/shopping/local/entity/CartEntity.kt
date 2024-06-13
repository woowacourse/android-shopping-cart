package woowacourse.shopping.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.local.converter.CartConverters

@Entity(tableName = "cart")
@TypeConverters(CartConverters::class)
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val product: Product,
    val productId: Long = product.id,
    val count: Int = product.count,
) {
    fun toCartItem(): CartProduct {
        return CartProduct(
            product = product,
            count = count,
        )
    }

    companion object {
        fun makeCartEntity(
            product: Product,
            count: Int,
        ): CartEntity {
            return CartEntity(product = product, count = count)
        }
    }
}
