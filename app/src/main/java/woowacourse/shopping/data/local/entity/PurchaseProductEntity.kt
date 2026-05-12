package woowacourse.shopping.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.PurchaseProduct

@Entity(tableName = "purchase_products")
data class PurchaseProductEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "count") val count: Int,
) {
    init {
        require(count > 0) { "상품의 개수는 1이상이어야 합니다." }
    }
}
