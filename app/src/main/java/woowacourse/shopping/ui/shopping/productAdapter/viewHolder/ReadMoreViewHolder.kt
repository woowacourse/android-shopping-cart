package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductReadMoreBinding
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener

class ReadMoreViewHolder private constructor(
    binding: ItemProductReadMoreBinding,
    listener: ProductsListener
) :
    ShoppingViewHolder(binding.root) {

    init {
        binding.listener = listener
    }

    override fun bind(productItemType: ProductsItemType) = Unit

    companion object {
        fun from(parent: ViewGroup, listener: ProductsListener): ReadMoreViewHolder {
            val binding = ItemProductReadMoreBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ReadMoreViewHolder(binding, listener)
        }
    }
}
