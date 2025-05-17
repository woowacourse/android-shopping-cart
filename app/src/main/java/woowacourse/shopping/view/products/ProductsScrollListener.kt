package woowacourse.shopping.view.products

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductsScrollListener(
    private val layoutManager: GridLayoutManager,
    private val loadMoreClick: (Boolean) -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val totalItemCount = recyclerView.adapter?.itemCount ?: return
        val canLoadMore = lastVisibleItemPosition >= totalItemCount - 1
        loadMoreClick(canLoadMore)
    }
}
