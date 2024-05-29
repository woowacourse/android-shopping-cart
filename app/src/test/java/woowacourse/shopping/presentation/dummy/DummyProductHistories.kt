package woowacourse.shopping.presentation.dummy

import woowacourse.shopping.data.db.producthistory.RecentProduct

class DummyProductHistories {
    val productHistories =
        mutableListOf(
            RecentProduct(1, 1),
            RecentProduct(2, 2),
            RecentProduct(3, 3),
            RecentProduct(4, 4),
            RecentProduct(5, 5),
        )
}
