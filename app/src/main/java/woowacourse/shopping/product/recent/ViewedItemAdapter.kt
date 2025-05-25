package woowacourse.shopping.product.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.event.CatalogEventHandlerImpl

class ViewedItemAdapter(
    private val handler: CatalogEventHandlerImpl,
) : RecyclerView.Adapter<ViewedItemHolder>() {
    private var viewedProducts: List<ProductUiModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewedItemHolder = ViewedItemHolder.from(parent, handler)

    override fun onBindViewHolder(
        holder: ViewedItemHolder,
        position: Int,
    ) = holder.bind(viewedProducts[position])

    override fun getItemCount(): Int = viewedProducts.size

    fun setData(newItems: List<ProductUiModel>) {
        viewedProducts = newItems
        notifyDataSetChanged()
    }
}
