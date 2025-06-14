package woowacourse.shopping.view.product.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ComponentRecentlyViewedProductsBinding
import woowacourse.shopping.domain.Product

class RecentlyViewedProductHolder(
    val binding: ComponentRecentlyViewedProductsBinding,
    val navigateToProductDetail: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(recentlyViewedProduct: ViewItems.RecentlyViewedProducts) {
        val adapter =
            RecentlyViewedProductAdapter(navigateToProductDetail)
        binding.rvRecentlyViewedProducts.apply {
            this.adapter = adapter
            adapter.updateData(recentlyViewedProduct.products)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            navigateToProductDetail: (Product) -> Unit,
        ): RecentlyViewedProductHolder {
            val binding =
                ComponentRecentlyViewedProductsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            return RecentlyViewedProductHolder(binding, navigateToProductDetail)
        }
    }
}
