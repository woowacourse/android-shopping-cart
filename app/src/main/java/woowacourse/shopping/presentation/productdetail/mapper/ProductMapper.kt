package woowacourse.shopping.presentation.productdetail.mapper

import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.presentation.productdetail.model.ProductUiModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Product.toUiModel(): ProductUiModel =
    ProductUiModel(
        productId = productId,
        imageUrl = imageUrl,
        productName = productName,
        price = price.value,
    )
