package woowacourse.shopping.ui.shopping.productAdapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.RecentProductItemBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductItem
import woowacourse.shopping.ui.shopping.recentProductAdapter.RecentProductsAdapter

class RecentViewHolder(
    private val binding: RecentProductItemBinding,
    private val onClickItem: (product: ProductUIModel) -> Unit
) : ItemViewHolder(binding.root) {
    override fun bind(productItemType: ProductsItemType) {
        val recentProducts = productItemType as? ProductsItemType.RecentProducts ?: return

        binding.recentProductRecyclerview.adapter = RecentProductsAdapter(
            recentProducts.product.map { RecentProductItem(it) },
            onClickItem
        )
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickItem: (product: ProductUIModel) -> Unit
        ): RecentViewHolder {
            val binding =
                RecentProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentViewHolder(binding, onClickItem)
        }
    }
}
