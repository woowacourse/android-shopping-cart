package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.uimodel.ProductUiModel

class RecentProductsAdapter(
    private val handler: ProductEventHandler,
) : RecyclerView.Adapter<RecentProductsViewHolder>() {
    private var productUiModels: List<ProductUiModel> = listOf()

    override fun getItemCount(): Int = productUiModels.size

    override fun onBindViewHolder(
        holder: RecentProductsViewHolder,
        position: Int,
    ) {
        val item = productUiModels[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductsViewHolder {
        return RecentProductsViewHolder(parent, handler)
    }

    fun updateProducts(productUiModels: List<ProductUiModel>) {
        val previousSize = itemCount
        this.productUiModels = productUiModels
        notifyItemRangeChanged(0, previousSize)
    }
}
