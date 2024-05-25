package woowacourse.shopping.presentation.ui.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyProductBinding
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler
import woowacourse.shopping.presentation.ui.shopping.viewholder.RecentlyProductViewHolder

class RecentlyProductAdapter(
    private val actionHandler: ShoppingActionHandler,
) : RecyclerView.Adapter<RecentlyProductViewHolder>() {
    private var recentlyProductItems: List<RecentlyViewedProduct> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyProductViewHolder {
        val binding = ItemRecentlyProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecentlyProductViewHolder,
        position: Int,
    ) {
        val recentlyViewedProduct = recentlyProductItems[position]
        holder.bind(recentlyViewedProduct, actionHandler)
    }

    override fun getItemCount(): Int {
        return recentlyProductItems.size
    }

    @Suppress("notifyDataSetChanged")
    fun loadData(recentlyProductItems: List<RecentlyViewedProduct>) {
        this.recentlyProductItems = recentlyProductItems
        notifyDataSetChanged()
    }
}
