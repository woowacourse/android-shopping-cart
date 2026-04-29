package woowacourse.shopping.feature.products.model

import androidx.compose.runtime.Stable
import woowacourse.shopping.domain.model.product.Product

@Stable
data class ShoppingProductInfo(
    val id: String,
    val productImageUrl: String,
    val productName: String,
    val price: String,
)

fun Product.toUiModel(): ShoppingProductInfo = ShoppingProductInfo(
    id = id,
    productImageUrl = imageUrl,
    productName = productTitle.value,
    price = "%,d원".format(price.value),
)
