package woowacourse.shopping.presentation.home

import woowacourse.shopping.data.model.RecentProduct

interface RecentProductListener {
    fun onUpdate(items: List<RecentProduct>)
}
