package woowacourse.shopping.view.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyProductBinding
import woowacourse.shopping.domain.model.RecentlyProduct
import woowacourse.shopping.view.products.OnClickProducts
import woowacourse.shopping.view.products.adapter.viewholder.RecentlyViewHolder

class RecentlyAdapter(
    private val onClickProducts: OnClickProducts,
) : RecyclerView.Adapter<RecentlyViewHolder>() {
    private var recentlyProducts: List<RecentlyProduct> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentlyViewHolder {
        val view = ItemRecentlyProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentlyViewHolder(view, onClickProducts)
    }

    override fun getItemCount(): Int {
        return recentlyProducts.size
    }

    override fun onBindViewHolder(
        holder: RecentlyViewHolder,
        position: Int,
    ) {
        val item = recentlyProducts[position]
        holder.bind(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(recentlyProducts: List<RecentlyProduct>) {
        this.recentlyProducts = recentlyProducts
        notifyDataSetChanged()
    }
}
