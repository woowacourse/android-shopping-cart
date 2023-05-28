package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.util.toMoneyFormat

@Parcelize
data class ProductUiModel(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val price: Int,
    var count: Int = 0
) : Parcelable {
    fun toMoneyFormat() = price.toMoneyFormat()
}
