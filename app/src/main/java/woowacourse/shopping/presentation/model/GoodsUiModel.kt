package woowacourse.shopping.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.domain.model.Goods

@Parcelize
data class GoodsUiModel(
    val name: String,
    val price: Int,
    val imageUrl: String,
) : Parcelable

fun Goods.toUiModel(): GoodsUiModel {
    return GoodsUiModel(
        name = this.name.value,
        price = this.price.value,
        imageUrl = this.imageUrl,
    )
}

fun GoodsUiModel.toDomainModel(): Goods {
    return Goods.of(
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
    )
}
