package woowacourse.shopping.feature.goods.adapter.vertical

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MoreButtonAdapter(
    private val moreButtonClickListener: MoreButtonClickListener,
) : RecyclerView.Adapter<MoreButtonViewHolder>() {
    private var isVisible: Boolean = true

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MoreButtonViewHolder = MoreButtonViewHolder.from(parent, moreButtonClickListener)

    override fun onBindViewHolder(
        holder: MoreButtonViewHolder,
        position: Int,
    ) {
    }

    override fun getItemCount() = if (isVisible) 1 else 0

    fun setVisibility(visible: Boolean) {
        if (!visible && isVisible) {
            isVisible = false
            notifyItemRemoved(0)
        }
    }
}
