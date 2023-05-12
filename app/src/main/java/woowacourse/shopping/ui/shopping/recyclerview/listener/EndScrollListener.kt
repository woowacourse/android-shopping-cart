package woowacourse.shopping.ui.shopping.recyclerview.listener

import androidx.recyclerview.widget.RecyclerView

class EndScrollListener(
    private val onEndScroll: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(1)) {
            onEndScroll()
        }
    }
}
