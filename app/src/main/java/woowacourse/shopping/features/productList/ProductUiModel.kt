package woowacourse.shopping.features.productList

import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

data class ProductUiModel(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int,
    val isExistProductToCart: Boolean,
)

fun ProductUiModel.toProduct(): Product =
    Product(
        id = id,
        name = ProductName(name),
        price = Price(price),
        imageUrl = ImageUrl(imageUrl),
    )
