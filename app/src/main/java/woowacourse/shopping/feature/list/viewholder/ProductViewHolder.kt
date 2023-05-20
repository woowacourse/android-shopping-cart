package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ProductView

class ProductViewHolder(
    val parent: ViewGroup,
    onClick: (Int) -> Unit,
) : ItemViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_product, parent, false),
) {
    private val binding = ItemProductBinding.bind(itemView)

    init {
        binding.root.setOnClickListener { onClick(adapterPosition) }
    }

    override fun bind(productView: ProductView) {
        val productItem = productView as ProductView.ProductItem
        binding.product = productItem
    }
}
