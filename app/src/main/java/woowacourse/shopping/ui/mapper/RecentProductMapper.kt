package woowacourse.shopping.ui.mapper

import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.ui.model.UiRecentProduct

fun UiRecentProduct.toDomain(): RecentProduct =
    RecentProduct(id = id, product = product.toDomain())

fun RecentProduct.toUi(): UiRecentProduct =
    UiRecentProduct(id = id, product = product.toUi())
