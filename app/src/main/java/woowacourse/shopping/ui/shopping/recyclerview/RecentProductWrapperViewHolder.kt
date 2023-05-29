package woowacourse.shopping.ui.shopping.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.utils.convertDpToPixel
import woowacourse.shopping.databinding.ListRecentProductLayoutBinding

class RecentProductWrapperViewHolder(
    private val binding: ListRecentProductLayoutBinding,
    itemCount: Int
) : RecyclerView.ViewHolder(binding.root) {

    init {
        setItemDecoration(lastPosition = itemCount - 1)
    }

    private fun setItemDecoration(lastPosition: Int) {
        binding.shoppingRecentProductList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)

                val density = binding.root.resources.displayMetrics.density
                val defaultHorizontalOffset =
                    convertDpToPixel(DP_DEFAULT_HORIZONTAL_OFFSET, density)
                val edgeHorizontalOffset = convertDpToPixel(DP_EDGE_HORIZONTAL_OFFSET, density)
                when (position) {
                    0 -> {
                        outRect.left = edgeHorizontalOffset
                        outRect.right = defaultHorizontalOffset
                    }
                    lastPosition -> {
                        outRect.left = defaultHorizontalOffset
                        outRect.right = edgeHorizontalOffset
                    }
                    else -> {
                        outRect.left = defaultHorizontalOffset
                        outRect.right = defaultHorizontalOffset
                    }
                }
            }
        })
    }

    fun bind(adapter: RecentProductAdapter) {
        binding.shoppingRecentProductList.adapter = adapter
    }

    companion object {
        private const val DP_DEFAULT_HORIZONTAL_OFFSET = 4
        private const val DP_EDGE_HORIZONTAL_OFFSET = 20
    }
}
