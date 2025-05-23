package woowacourse.shopping.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.domain.model.ShoppingCartItem

@Parcelize
data class ShoppingCartItemUiModel(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int,
) : Parcelable

fun ShoppingCartItem.toUiModel(): ShoppingCartItemUiModel {
    val goods = this.goods
    return ShoppingCartItemUiModel(
        id = goods.id,
        name = goods.name.value,
        price = goods.price.value,
        imageUrl = goods.imageUrl,
        quantity = this.quantity,
    )
}

fun ShoppingCartItemUiModel.toDomain(): ShoppingCartItem {
    return ShoppingCartItem(
        goods = Goods.of(
            id = this.id,
            name = this.name,
            price = this.price,
            imageUrl = this.imageUrl
        ),
        quantity = this.quantity,
    )
}