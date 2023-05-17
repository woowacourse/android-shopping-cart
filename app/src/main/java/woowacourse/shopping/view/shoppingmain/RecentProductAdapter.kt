package woowacourse.shopping.view.shoppingmain

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.uimodel.ProductUIModel
import woowacourse.shopping.model.uimodel.RecentProductUIModel

class RecentProductAdapter(
    private var recentProducts: List<RecentProductUIModel>,
    private val productOnClick: (ProductUIModel) -> Unit,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductViewHolder {
        return RecentProductViewHolder(parent, productOnClick)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    override fun getItemCount(): Int = recentProducts.size

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(recentProducts[position].productUIModel)
    }

    fun update(updatedRecentProducts: List<RecentProductUIModel>) {
        recentProducts = updatedRecentProducts
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE = 2
    }
}
