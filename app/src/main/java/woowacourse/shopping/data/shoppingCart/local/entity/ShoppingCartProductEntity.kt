package woowacourse.shopping.data.shoppingCart.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import woowacourse.shopping.data.product.local.entity.ProductEntity
import woowacourse.shopping.data.product.local.entity.toEntity
import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct

@Entity(
    tableName = "shoppingCart",
    indices = [Index(value = ["product_id"], unique = true)],
)
data class ShoppingCartProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @Embedded(prefix = "product_") val product: ProductEntity,
    val quantity: Int,
) {
    fun toDomain(): ShoppingCartProduct =
        ShoppingCartProduct(
            product = product.toDomain(),
            quantity = quantity,
        )
}

fun ShoppingCartProduct.toEntity(): ShoppingCartProductEntity =
    ShoppingCartProductEntity(
        product = product.toEntity(),
        quantity = quantity,
    )
