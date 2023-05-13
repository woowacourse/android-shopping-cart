package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductReadMoreBinding
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType

class ReadMoreViewHolder private constructor(
    binding: ItemProductReadMoreBinding,
    onReadMoreClick: () -> Unit
) :
    ItemViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onReadMoreClick() }
    }

    override fun bind(productItemType: ProductsItemType) = Unit

    companion object {
        fun from(parent: ViewGroup, onReadMoreClick: () -> Unit): ReadMoreViewHolder {
            val binding = ItemProductReadMoreBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ReadMoreViewHolder(binding, onReadMoreClick)
        }
    }
}
