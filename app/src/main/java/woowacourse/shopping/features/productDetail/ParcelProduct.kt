package woowacourse.shopping.features.productDetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.shopping.domain.product.model.ImageUrl
import woowacourse.shopping.domain.product.model.Price
import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.model.ProductName

@Parcelize
data class ParcelProduct(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
) : Parcelable

fun Product.toParcelProduct(): ParcelProduct =
    ParcelProduct(
        id = id,
        name = name.value,
        price = price.value,
        imageUrl = imageUrl.value,
    )

fun ParcelProduct.toProduct(): Product =
    Product(
        id = id,
        name = ProductName(name),
        price = Price(price),
        imageUrl = ImageUrl(imageUrl),
    )
