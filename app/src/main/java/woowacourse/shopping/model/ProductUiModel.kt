package woowacourse.shopping.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat

@Parcelize
data class ProductUiModel(
    val id: Long,
    val name: String,
    val imgUrl: String,
    val price: Int,
    var count: Int = 0
) : Parcelable {
    fun toPriceFormat(): String {
        return DecimalFormat("#,###").format(price)
    }
}
