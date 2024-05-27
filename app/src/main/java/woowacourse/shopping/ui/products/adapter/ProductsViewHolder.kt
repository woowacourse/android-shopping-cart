package woowacourse.shopping.ui.products.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductsBinding
import woowacourse.shopping.ui.products.adapter.recent.OnClickRecentProductItem
import woowacourse.shopping.ui.products.adapter.recent.RecentProductUiModel
import woowacourse.shopping.ui.products.adapter.recent.RecentProductsAdapter
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import woowacourse.shopping.ui.utils.AddCartQuantityBundle
import woowacourse.shopping.ui.utils.OnDecreaseProductQuantity
import woowacourse.shopping.ui.utils.OnIncreaseProductQuantity

sealed class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(private val binding: ItemProductBinding) :
        ProductsViewHolder(binding.root) {
        fun bind(
            productUiModel: ProductUiModel,
            onClickProductItem: OnClickProductItem,
            onIncreaseProductQuantity: OnIncreaseProductQuantity,
            onDecreaseProductQuantity: OnDecreaseProductQuantity,
        ) {
            binding.productUiModel = productUiModel
            binding.addCartQuantityBundle =
                AddCartQuantityBundle(
                    productUiModel.productId,
                    productUiModel.quantity,
                    onIncreaseProductQuantity,
                    onDecreaseProductQuantity,
                )
            binding.ivProduct.setOnClickListener {
                onClickProductItem(productUiModel.productId)
            }
        }
    }

    class RecentProductsViewHolder(
        binding: ItemRecentProductsBinding,
        onClickRecentProductItem: OnClickRecentProductItem,
    ) : ProductsViewHolder(binding.root) {
        private val adapter by lazy {
            RecentProductsAdapter(onClickRecentProductItem = onClickRecentProductItem)
        }

        init {
            binding.rvRecentProduct.itemAnimator = null
            binding.rvRecentProduct.adapter = adapter
        }

        fun bind(recentProducts: List<RecentProductUiModel>) {
            adapter.submitList(recentProducts)
        }
    }
}

typealias OnClickProductItem = (productId: Long) -> Unit
