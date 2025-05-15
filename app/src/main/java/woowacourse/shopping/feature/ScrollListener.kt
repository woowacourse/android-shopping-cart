package woowacourse.shopping.feature

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollListener(
    private val shouldShowButton: () -> Boolean,
    private val onVisibilityChange: (visible: Boolean) -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        val lastItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = layoutManager.itemCount
        val shouldShow = lastItemPosition == totalItemCount - 1 && shouldShowButton()

        onVisibilityChange(shouldShow)
    }
}
