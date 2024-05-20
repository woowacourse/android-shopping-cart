package woowacourse.shopping.domain.model

class RecentViewedProducts {
    private var _productIds: MutableList<Long> = mutableListOf()
    val productIds: List<Long>
        get() = _productIds.toList()
}
