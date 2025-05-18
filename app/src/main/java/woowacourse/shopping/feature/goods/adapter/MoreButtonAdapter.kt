package woowacourse.shopping.feature.goods.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MoreButtonAdapter(
    private val onClick: () -> Unit,
) : RecyclerView.Adapter<MoreButtonViewHolder>() {
    private var isVisible: Boolean = true

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MoreButtonViewHolder = MoreButtonViewHolder.from(parent, onClick)

    override fun onBindViewHolder(
        holder: MoreButtonViewHolder,
        position: Int,
    ) {}

    override fun getItemCount() = if (isVisible) 1 else 0

    fun setVisibility(visible: Boolean) {
        if (!visible && isVisible) {
            isVisible = false
            notifyItemRemoved(0)
        }
    }
}
