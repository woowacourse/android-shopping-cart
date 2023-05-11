package woowacourse.shopping.presentation.productlist.product

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreBinding

class MoreItemViewHolder(
    binding: ItemMoreBinding,
    showMoreProductItem: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener { showMoreProductItem() }
    }
}
