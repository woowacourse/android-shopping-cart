package woowacourse.shopping.feature.main.recent

import woowacourse.shopping.model.RecentProductUiModel

data class RecentProductItemModel(
    val recentProduct: RecentProductUiModel,
    val onClick: (productId:Long) -> Unit
)
