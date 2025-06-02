package woowacourse.shopping.data.entity

import androidx.room.ForeignKey
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import woowacourse.shopping.domain.model.LastProduct
import woowacourse.shopping.domain.model.Product
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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
    tableName = "lastProduct"
)
data class LastProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val viewedAt: Long = System.currentTimeMillis(),
    val productId: Int,
)

fun LastProductEntity.toDomain(product: Product): LastProduct {
    val time = this.viewedAt
    return LastProduct(product, time)
}