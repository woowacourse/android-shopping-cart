package woowacourse.shopping.view.util.scroll

import androidx.recyclerview.widget.RecyclerView

class ScrollEndEvent(
    private val onScrollEnd: () -> Unit,
    private val onScrollReset: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        if (
            !recyclerView.canScrollVertically(
                RECYCLER_VIEW_END_POSITION,
            )
        ) {
            onScrollEnd()
        } else if (dy < RECYCLER_VIEW_END_POSITION) {
            onScrollReset()
        }
    }

    companion object {
        private const val RECYCLER_VIEW_END_POSITION = 1
    }
}
