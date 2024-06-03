package woowacourse.shopping.ui.products.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductsBinding
import woowacourse.shopping.ui.products.ProductsListener
import woowacourse.shopping.ui.products.adapter.recent.OnClickRecentProductItem
import woowacourse.shopping.ui.products.adapter.recent.RecentProductUiModel
import woowacourse.shopping.ui.products.adapter.recent.RecentProductsAdapter
import woowacourse.shopping.ui.products.adapter.type.ProductUiModel
import woowacourse.shopping.ui.utils.AddCartQuantityBundle

sealed class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(private val binding: ItemProductBinding) :
        ProductsViewHolder(binding.root) {
        fun bind(
            productUiModel: ProductUiModel,
            productsListener: ProductsListener,
        ) {
            binding.product = productUiModel
            binding.listener = productsListener
            binding.addCartQuantityBundle =
                AddCartQuantityBundle(
                    productUiModel.productId,
                    productUiModel.quantity,
                    productsListener::onClickIncreaseQuantity,
                    productsListener::onClickDecreaseQuantity,
                )
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
