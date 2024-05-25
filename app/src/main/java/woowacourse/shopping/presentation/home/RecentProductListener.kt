package woowacourse.shopping.presentation.home

import woowacourse.shopping.data.model.history.RecentProduct

interface RecentProductListener {
    fun onUpdate(items: List<RecentProduct>)
}
