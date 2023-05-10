package woowacourse.shopping.shopping

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.productdetail.ProductUiModel

class RecentViewedRecyclerAdapter(private val products: List<ProductUiModel>) :
    RecyclerView.Adapter<RecentViewedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewedViewHolder {

        return RecentViewedViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecentViewedViewHolder, position: Int) {

        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
