package woowacourse.shopping.feature.model.mapper

import com.example.domain.RecentProducts
import woowacourse.shopping.feature.model.RecentProductCacheState

fun RecentProducts.toUi(): RecentProductCacheState {
    return RecentProductCacheState(products.map { it.toUi() })
}
