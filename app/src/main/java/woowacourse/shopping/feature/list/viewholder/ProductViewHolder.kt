package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ProductView

class ProductViewHolder(
    val parent: ViewGroup,
) : ItemViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_product, parent, false),
) {
    private val binding = ItemProductBinding.bind(itemView)

    override fun bind(productView: ProductView, onClick: (ProductView) -> Unit) {
        val productItem = productView as ProductView.ProductItem

        binding.product = productItem
        binding.root.setOnClickListener { onClick(productItem) }
    }
}
