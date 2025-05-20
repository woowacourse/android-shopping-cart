package woowacourse.shopping.view.util.scroll

import androidx.recyclerview.widget.RecyclerView

class ScrollEndEvent(
    private val isLoadable: () -> Boolean?,
    private val onScrollEnd: () -> Unit,
    private val onScrollReset: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        if (isLoadable()?.not() ?: return &&
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
