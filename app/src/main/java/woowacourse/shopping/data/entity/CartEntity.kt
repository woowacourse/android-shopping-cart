package woowacourse.shopping.data.entity

import androidx.room.ForeignKey
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.domain.model.CartProduct

@Entity(
    foreignKeys = [
        ForeignKey(
            entity =  ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["productId"])],
    tableName = "cart"
)
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val count : Int = 1
)

fun CartEntity.toDomain(productDao: ProductDao): CartProduct? {
    val productEntity = productDao.getById(this.productId) ?: return null
    val product = productEntity.toDomain()
    return CartProduct(product, count)
}

fun CartProduct.toEntity(): CartEntity {
    return CartEntity(productId = product.id, count = count)
}

