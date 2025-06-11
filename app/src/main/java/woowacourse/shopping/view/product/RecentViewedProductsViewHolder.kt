package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentViewedProductsBinding
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.view.product.ProductsItem.RecentViewedProductsItem

class RecentViewedProductsViewHolder(
    private val binding: ItemRecentViewedProductsBinding,
    onSelectProduct: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private val adapter = RecentViewedProductsAdapter(onSelectProduct)

    init {
        binding.adapter = adapter
    }

    fun bind(item: RecentViewedProductsItem) {
        binding.visible = item.products.isNotEmpty()
        adapter.submitList(item.products)
    }

    companion object {
        fun of(
            parent: ViewGroup,
            onSelectProduct: (Product) -> Unit,
        ): RecentViewedProductsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentViewedProductsBinding.inflate(layoutInflater, parent, false)
            return RecentViewedProductsViewHolder(binding, onSelectProduct)
        }
    }
}
