package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.feature.main.recent.RecentProductItemModel
import java.time.LocalDateTime

@Parcelize
data class RecentProductUiModel(
    val productUiModel: ProductUiModel,
    val dateTime: LocalDateTime
) : Parcelable {
    fun toItemModel(onClick: (productId: Long) -> Unit): RecentProductItemModel {
        return RecentProductItemModel(this, onClick)
    }
}
