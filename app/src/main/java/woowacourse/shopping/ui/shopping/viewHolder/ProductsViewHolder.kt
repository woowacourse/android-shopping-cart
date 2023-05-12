package woowacourse.shopping.ui.shopping.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.ui.shopping.ProductsItemType

class ProductsViewHolder private constructor(
    private val binding: ProductItemBinding,
    val onClickItem: (Int) -> Unit
) :
    ItemViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClickItem(bindingAdapterPosition)
        }
    }

    override fun bind(productItemType: ProductsItemType) {
        val productItem = productItemType as? ProductsItemType.Product ?: return
        binding.product = productItem.product
    }

    companion object {
        fun from(parent: ViewGroup, onClickItem: (Int) -> Unit): ProductsViewHolder {
            val binding = ProductItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductsViewHolder(binding, onClickItem)
        }
    }
}
