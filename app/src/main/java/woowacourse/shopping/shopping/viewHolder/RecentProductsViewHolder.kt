package woowacourse.shopping.shopping.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.RecentProductItemBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.recentProduct.RecentProductItem
import woowacourse.shopping.recentProduct.RecentProductsAdapter
import woowacourse.shopping.shopping.ProductsItemType
import woowacourse.shopping.shopping.RecentProductsItem

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
