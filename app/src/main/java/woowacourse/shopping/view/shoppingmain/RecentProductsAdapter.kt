package woowacourse.shopping.view.shoppingmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductRecentBinding
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

class RecentProductsAdapter(
    private val recentProducts: List<RecentProductUIModel>,
    private val productOnClick: (ProductUIModel) -> Unit,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentProductViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = ItemProductRecentBinding.inflate(inflater, parent, false)
        return RecentProductViewHolder(view, recentProducts, productOnClick)
    }

    override fun getItemCount(): Int = recentProducts.size

    override fun onBindViewHolder(holder: RecentProductViewHolder, position: Int) {
        holder.bind(recentProducts[position].productUIModel)
    }
}
