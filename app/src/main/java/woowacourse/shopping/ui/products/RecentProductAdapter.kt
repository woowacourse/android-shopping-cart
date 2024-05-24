package woowacourse.shopping.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.model.Product

class RecentProductAdapter(
    private val viewModel: ProductContentsViewModel,
    private val lifecycleOwner: LifecycleOwner,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    private var products: List<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val binding = ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentProductViewHolder(binding, lifecycleOwner)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position], viewModel)
    }

    fun setData(newProducts: List<Product>)  {
        products = newProducts
        notifyItemRangeChanged(0, newProducts.size)
    }
}
