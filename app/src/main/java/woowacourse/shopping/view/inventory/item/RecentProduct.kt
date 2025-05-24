package woowacourse.shopping.view.inventory.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class RecentProduct(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val lastViewed: LocalDateTime,
) : Parcelable
