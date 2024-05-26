package woowacourse.shopping.presentation.home.history

import woowacourse.shopping.data.model.history.RecentProduct

interface RecentProductListener {
    fun onUpdate(items: List<RecentProduct>)
}
