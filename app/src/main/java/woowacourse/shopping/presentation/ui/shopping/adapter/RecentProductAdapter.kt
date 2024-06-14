package woowacourse.shopping.presentation.ui.shopping.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.RecentProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingEventHandler
import woowacourse.shopping.presentation.ui.shopping.adapter.viewholder.RecentViewHolder

class RecentProductAdapter(
    private val onClickProducts: ShoppingEventHandler,
) : RecyclerView.Adapter<RecentViewHolder>() {
    private var recentProducts: List<RecentProduct> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentViewHolder {
        val view = ItemRecentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(view, onClickProducts)
    }

    override fun getItemCount(): Int {
        return recentProducts.size
    }

    override fun onBindViewHolder(
        holder: RecentViewHolder,
        position: Int,
    ) {
        val item = recentProducts[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(recentlyProducts: List<RecentProduct>) {
        this.recentProducts = recentlyProducts
        notifyDataSetChanged()
    }
}
