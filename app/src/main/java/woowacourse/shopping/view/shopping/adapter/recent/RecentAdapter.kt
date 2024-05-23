package woowacourse.shopping.view.shopping.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.data.db.shopping.DummyShopping
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.shopping.ShoppingClickListener

class RecentAdapter(private val clickListener: ShoppingClickListener) :
    RecyclerView.Adapter<RecentViewHolder>() {
    private var recentViewedProducts: List<Product> = DummyShopping.items

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentViewHolder {
        val binding =
            ItemRecentViewedProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recentViewedProducts.size
    }

    override fun onBindViewHolder(
        holder: RecentViewHolder,
        position: Int,
    ) {
        val recentViewedProduct = recentViewedProducts[position]
        return holder.bind(recentViewedProduct, clickListener)
    }
}
