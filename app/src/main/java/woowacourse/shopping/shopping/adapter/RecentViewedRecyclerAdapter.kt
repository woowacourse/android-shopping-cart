package woowacourse.shopping.shopping.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.RecentViewedProductUiModel
import woowacourse.shopping.shopping.adapter.viewholder.RecentViewedProductViewHolder

class RecentViewedRecyclerAdapter(
    private val products: List<RecentViewedProductUiModel>,
    private val onProductClicked: (productId: Int) -> Unit,
) :
    RecyclerView.Adapter<RecentViewedProductViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentViewedProductViewHolder {

        return RecentViewedProductViewHolder.from(
            parent = parent,
            onProductImageClicked = onProductClicked
        )
    }

    override fun onBindViewHolder(holder: RecentViewedProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
