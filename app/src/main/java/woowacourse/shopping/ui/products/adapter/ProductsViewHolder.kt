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
import woowacourse.shopping.ui.utils.AddQuantityListener

sealed class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val productsListener: ProductsListener,
    ) :
        ProductsViewHolder(binding.root) {
        fun bind(productUiModel: ProductUiModel) {
            binding.product = productUiModel
            binding.listener = productsListener
            binding.quantityListener = object : AddQuantityListener {
                override fun onIncreaseProductQuantity() {
                    productsListener.onClickIncreaseQuantity(productUiModel.productId)
                }

                override fun onDecreaseProductQuantity() {
                    productsListener.onClickDecreaseQuantity(productUiModel.productId)
                }

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
