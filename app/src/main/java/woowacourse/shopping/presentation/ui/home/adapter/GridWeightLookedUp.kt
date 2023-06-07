package woowacourse.shopping.presentation.ui.home.adapter

import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.RECENTLY_VIEWED
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.SHOW_MORE

class GridWeightLookedUp(
    private val getItemViewType: (position: Int) -> Int,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return when (getItemViewType(position)) {
            RECENTLY_VIEWED.ordinal, SHOW_MORE.ordinal -> 2
            else -> 1
        }
    }
}
