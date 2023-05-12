package woowacourse.shopping.ui.shopping.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ProductReadMoreItemBinding
import woowacourse.shopping.ui.shopping.ProductsItemType

class ReadMoreViewHolder private constructor(
    binding: ProductReadMoreItemBinding,
    onReadMoreClick: () -> Unit
) :
    ItemViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onReadMoreClick() }
    }

    override fun bind(productItemType: ProductsItemType) = Unit

    companion object {
        fun from(parent: ViewGroup, onReadMoreClick: () -> Unit): ReadMoreViewHolder {
            val binding = ProductReadMoreItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ReadMoreViewHolder(binding, onReadMoreClick)
        }
    }
}
