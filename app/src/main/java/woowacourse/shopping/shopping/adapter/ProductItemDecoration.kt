package woowacourse.shopping.shopping.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.shopping.adapter.viewholder.ShoppingRecyclerItemViewType

class ProductItemDecoration(
    private val getItemViewType: (position: Int) -> Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (getItemViewType(position) == ShoppingRecyclerItemViewType.PRODUCT.ordinal) {
            when (position % 2) {
                0 -> outRect.left = LEFT_HALF_MARGIN
                1 -> outRect.left = LEFT_MARGIN
            }
        }
    }

    companion object {
        private const val LEFT_MARGIN = 80
        private const val LEFT_HALF_MARGIN = LEFT_MARGIN / 2
    }
}
