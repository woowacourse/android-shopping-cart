package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.db.recently.RecentlyProductDatabase.Companion.RECENTLY_ITEM_DB_NAME
import woowacourse.shopping.domain.model.RecentlyProduct

@Entity(tableName = RECENTLY_ITEM_DB_NAME)
data class RecentlyProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val productId: Long,
    val imageUrl: String,
    val name: String,
){
    fun toRecentlyProduct(): RecentlyProduct{
        return RecentlyProduct(
            id = id,
            productId = productId,
            imageUrl = imageUrl,
            name = name,
        )
    }

    companion object {
        fun makeRecentlyProductEntity(
            recentlyProduct: RecentlyProduct,
        ): RecentlyProductEntity{
            return RecentlyProductEntity(
                productId = recentlyProduct.productId,
                name = recentlyProduct.name,
                imageUrl = recentlyProduct.imageUrl,
            )
        }
    }
}
