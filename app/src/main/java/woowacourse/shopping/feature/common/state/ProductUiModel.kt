package woowacourse.shopping.feature.common.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.feature.format.DecimalPriceFormatter

@Parcelize
data class ProductUiModel(
    val name: String,
    val price: Int,
    val imageUrl: String,
    val id: String,
    val quantity: Int,
) : Parcelable {
    fun formattedPrice(quantity: Int = 1): String = DecimalPriceFormatter().format(price * quantity)
}
