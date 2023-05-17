package woowacourse.shopping.ui.shopping.morebutton

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemButtonMoreBinding
import woowacourse.shopping.util.setThrottleFirstOnClickListener

class MoreButtonViewHolder(parent: ViewGroup, onItemClick: () -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_button_more, parent, false)
    ) {
    private val binding = ItemButtonMoreBinding.bind(itemView)

    init {
        binding.tvMore.setThrottleFirstOnClickListener { onItemClick() }
    }
}
