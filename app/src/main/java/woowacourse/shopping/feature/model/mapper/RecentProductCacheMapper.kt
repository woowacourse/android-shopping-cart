package woowacourse.shopping.feature.model.mapper

import com.example.domain.RecentProductsCache
import woowacourse.shopping.feature.model.RecentProductCacheState

fun RecentProductsCache.toUi(): RecentProductCacheState {
    return RecentProductCacheState(products.map { it.toUi() })
}
