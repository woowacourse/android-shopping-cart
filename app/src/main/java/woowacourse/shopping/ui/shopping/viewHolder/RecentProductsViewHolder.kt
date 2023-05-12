package woowacourse.shopping.ui.shopping.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.RecentProductItemBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.recentProduct.RecentProductItem
import woowacourse.shopping.ui.recentProduct.RecentProductsAdapter
import woowacourse.shopping.ui.shopping.ProductsItemType
import woowacourse.shopping.ui.shopping.RecentProductsItem

class RecentProductsViewHolder(
    private val binding: RecentProductItemBinding,
    private val onClickItem: (product: ProductUIModel) -> Unit
) : ItemViewHolder(binding.root) {
    fun bind(productItemType: ProductsItemType) {
        val recentProducts = productItemType as? RecentProductsItem ?: return
        binding.recentProductRecyclerview.adapter = RecentProductsAdapter(
            recentProducts.product.map { RecentProductItem(it) },
            onClickItem
        )
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickItem: (product: ProductUIModel) -> Unit
        ): RecentProductsViewHolder {
            val binding =
                RecentProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RecentProductsViewHolder(binding, onClickItem)
        }
    }
}
