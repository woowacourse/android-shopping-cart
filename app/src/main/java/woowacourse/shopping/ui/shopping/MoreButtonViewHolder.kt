package woowacourse.shopping.ui.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemButtonMoreBinding

class MoreButtonViewHolder(
    binding: ItemButtonMoreBinding,
    onItemClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onItemClick() }
    }
}
