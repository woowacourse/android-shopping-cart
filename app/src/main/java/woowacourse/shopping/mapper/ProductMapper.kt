package woowacourse.shopping.mapper

import com.example.domain.model.Product
import woowacourse.shopping.model.ProductUiModel

fun Product.toPresentation(): ProductUiModel =
    ProductUiModel(id, name, imgUrl, price.value)
