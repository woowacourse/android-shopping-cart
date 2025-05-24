package woowacourse.shopping.fixture

import woowacourse.shopping.domain.RecentProduct
import java.time.LocalDateTime

object TestRecentProducts {
    val recentProducts =
        mutableListOf(
            RecentProduct(
                TestProducts.productUiModels[0],
                LocalDateTime.of(2025, 4, 11, 0, 0, 0),
            ),
            RecentProduct(
                TestProducts.productUiModels[1],
                LocalDateTime.of(2025, 4, 11, 0, 0, 0),
            ),
        )
}
