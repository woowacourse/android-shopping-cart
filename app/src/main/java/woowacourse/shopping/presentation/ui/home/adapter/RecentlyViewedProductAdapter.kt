package woowacourse.shopping.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedItemViewHolder

class RecentlyViewedProductAdapter(
    private val onClick: (Product) -> Unit,
) : RecyclerView.Adapter<RecentlyViewedItemViewHolder>() {
    private lateinit var layoutInflater: LayoutInflater
    private val viewItems: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewedItemViewHolder {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)

        return RecentlyViewedItemViewHolder(
            RecentlyViewedItemViewHolder.getView(parent, layoutInflater),
            onClick,
        )
    }

    override fun onBindViewHolder(holder: RecentlyViewedItemViewHolder, position: Int) {
        holder.bind(viewItems[position])
    }

    override fun getItemCount(): Int = viewItems.size

    fun fetchRecentProducts(recentProduct: List<Product>) {
        viewItems.clear()
        viewItems.addAll(recentProduct)

        notifyDataSetChanged()
    }
}
