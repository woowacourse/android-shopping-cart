package woowacourse.shopping.ui.shopping.morebutton

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.ShoppingViewType

class MoreButtonAdapter(
    private val onButtonClick: () -> Unit
) :
    RecyclerView.Adapter<MoreButtonViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreButtonViewHolder = MoreButtonViewHolder(parent, onButtonClick)

    private var hasNext: Boolean = false

    fun updateItemCount(hasNext: Boolean) {
        if (hasNext == this.hasNext) return
        this.hasNext = hasNext
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MoreButtonViewHolder, position: Int) {}

    override fun getItemCount(): Int = if (hasNext) 1 else 0

    override fun getItemViewType(position: Int): Int = ShoppingViewType.MORE_BUTTON.value
}
