package woowacourse.shopping.presentation.shopping.product

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ProductItemDecoration(private val offset: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position.isLeftSpan()) {
            outRect.right = offset / SPAN_COUNT
            outRect.bottom = offset
            return
        }
        outRect.left = offset / SPAN_COUNT
        outRect.bottom = offset
    }

    private fun Int.isLeftSpan(): Boolean = this % SPAN_COUNT == 0

    companion object {
        private const val SPAN_COUNT = 2
    }
}
