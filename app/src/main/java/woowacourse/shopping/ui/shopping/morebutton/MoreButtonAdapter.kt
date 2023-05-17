package woowacourse.shopping.ui.shopping.morebutton

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.ui.shopping.ShoppingViewType

class MoreButtonAdapter(
    private val onButtonClick: () -> Unit,
    var hasNext: Boolean = false
) :
    RecyclerView.Adapter<MoreButtonViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoreButtonViewHolder = MoreButtonViewHolder(parent, onButtonClick)

    override fun onBindViewHolder(holder: MoreButtonViewHolder, position: Int) {}

    override fun getItemCount(): Int = if (hasNext) 1 else 0

    override fun getItemViewType(position: Int): Int = ShoppingViewType.MORE_BUTTON.value
}
