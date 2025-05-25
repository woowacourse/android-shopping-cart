package woowacourse.shopping.data.recent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ViewedItemEntity")
data class ViewedItem(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    val id: Long,
    val imageUrl: String,
    val name: String,
    val price: Int,
)
