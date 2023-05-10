package woowacourse.shopping.presentation.ui.home.adapter

import androidx.recyclerview.widget.GridLayoutManager

class GridWeightLookedUp(
    private val getItemViewType: (position: Int) -> Int,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        if (getItemViewType(position) == HomeViewType.RECENTLY_VIEWED.ordinal) {
            return 2
        }
        return 1
    }
}
