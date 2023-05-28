package woowacourse.shopping.mapper

import com.example.domain.model.RecentProduct
import woowacourse.shopping.model.RecentProductUiModel

fun RecentProduct.toPresentation(): RecentProductUiModel =
    RecentProductUiModel(product.toPresentation(), dateTime)

fun RecentProductUiModel.toDomain(): RecentProduct =
    RecentProduct(productUiModel.toDomain(), dateTime)
