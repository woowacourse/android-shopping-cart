package woowacourse.shopping.ui.state

import android.os.Parcelable
import java.text.DecimalFormat
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUiModel(val title: String, val price: String, val imageUrl: String, val id: String) : Parcelable {
    companion object {
        fun of(name: String, price: Int, imageUrl: String, id: String): ProductUiModel {
            val formattedPrice = "${
                DecimalFormat("#,###").format(price)
            }원"
            return ProductUiModel(
                title = name,
                price = formattedPrice,
                imageUrl = imageUrl,
                id = id,
            )
        }
    }
}
