package woowacourse.shopping.view.product.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewProductBinding
import woowacourse.shopping.domain.Product

class RecentlyViewedProductAdapter(
    private val navigateToProductDetail: (Product) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ItemRecentlyViewProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ViewHolder(binding, navigateToProductDetail)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateData(recentlyViewedProducts: List<Product>) {
        this.products = recentlyViewedProducts
    }
}
