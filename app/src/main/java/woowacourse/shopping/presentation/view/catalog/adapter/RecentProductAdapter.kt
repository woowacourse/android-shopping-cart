package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.ProductUiModel

class RecentProductAdapter : RecyclerView.Adapter<RecentProductItemViewHolder>() {
    private val items = mutableListOf<ProductUiModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductItemViewHolder = RecentProductItemViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: RecentProductItemViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(products: List<ProductUiModel>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }
}
