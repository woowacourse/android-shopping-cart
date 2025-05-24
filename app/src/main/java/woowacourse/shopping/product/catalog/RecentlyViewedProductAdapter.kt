package woowacourse.shopping.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentlyViewedProductAdapter(
    products: List<ProductUiModel>,
) : RecyclerView.Adapter<RecentlyViewedProductViewHolder>() {
    private val products: MutableList<ProductUiModel> = products.toMutableList()

    fun setItems(products: List<ProductUiModel>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedProductViewHolder = RecentlyViewedProductViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: RecentlyViewedProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
