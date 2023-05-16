package woowacourse.shopping.mapper

import com.shopping.domain.Price
import com.shopping.domain.Product
import woowacourse.shopping.uimodel.ProductUIModel

fun ProductUIModel.toDomain() =
    Product(id, imageUrl, name, Price(price))

fun Product.toUIModel() =
    ProductUIModel(id, imageUrl, name, price.value)
