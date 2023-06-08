package woowacourse.shopping.common.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreBinding

class LoadMoreViewHolder(
    binding: ViewDataBinding,
    val onClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    val binding = binding as ItemMoreBinding

    fun bind() {
        binding.moreTv.setOnClickListener { onClick() }
    }
}
