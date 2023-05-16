package woowacourse.shopping.list.viewholder

import androidx.databinding.ViewDataBinding
import woowacourse.shopping.databinding.ItemMoreBinding

class LoadMoreViewHolder(
    val binding: ViewDataBinding,
    val onClick: () -> Unit
) : ItemHolder(binding) {

    override fun bind() {
        binding as ItemMoreBinding

        binding.moreTv.setOnClickListener { onClick() }
    }
}
