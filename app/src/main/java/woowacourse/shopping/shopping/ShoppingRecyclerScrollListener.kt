package woowacourse.shopping.shopping

import androidx.recyclerview.widget.RecyclerView

class ShoppingRecyclerScrollListener(
    private val scrollPossible: () -> Unit,
    private val scrollImpossible: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (recyclerView.canScrollVertically(DOWN)) {
            scrollPossible()
        } else {
            scrollImpossible()
        }
    }

    companion object {
        private const val DOWN = 1
    }
}
