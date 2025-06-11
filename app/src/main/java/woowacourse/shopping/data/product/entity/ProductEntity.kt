package woowacourse.shopping.data.product.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.domain.product.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Int,
) {
    fun toDomain(): Product = Product(id, name, price)

    companion object {
        fun Product.toEntity(): ProductEntity = ProductEntity(id, name, price)
    }
}
