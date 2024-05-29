package woowacourse.shopping.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.shopping.data.db.recently.RecentlyProductDatabase.Companion.RECENTLY_ITEM_DB_NAME

@Entity(tableName = RECENTLY_ITEM_DB_NAME)
data class RecentlyProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val productId: Long,
    val imageUrl: String,
    val name: String,
)
