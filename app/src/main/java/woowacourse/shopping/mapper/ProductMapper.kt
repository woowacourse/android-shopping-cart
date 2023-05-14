package woowacourse.shopping.mapper

import com.shopping.domain.Product
import woowacourse.shopping.uimodel.ProductUIModel

fun ProductUIModel.toDomain() =
    Product(url, name, price)

fun Product.toUIModel() =
    ProductUIModel(url, name, price)
