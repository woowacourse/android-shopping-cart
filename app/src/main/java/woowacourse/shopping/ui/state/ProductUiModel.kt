package woowacourse.shopping.ui.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.ui.format.Formatter

@Parcelize
data class ProductUiModel(val title: String, val price: String, val imageUrl: String, val id: String) : Parcelable {
    companion object {
        fun of(
            name: String,
            price: Int,
            imageUrl: String,
            id: String,
        ): ProductUiModel {
            return ProductUiModel(
                title = name,
                price = Formatter.priceFormat(price),
                imageUrl = imageUrl,
                id = id,
            )
        }
    }
}
