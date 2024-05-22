package woowacourse.shopping.view.shopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLoadMoreButtonBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.view.shopping.ShoppingClickListener
import woowacourse.shopping.view.shopping.adapter.ShoppingType.Companion.PRODUCT_VIEW_TYPE

class ShoppingAdapter(
    val clickListener: ShoppingClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val shoppingTypes: MutableList<ShoppingType> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return shoppingTypes[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == PRODUCT_VIEW_TYPE) {
            ProductViewHolder(ItemProductBinding.inflate(inflater, parent, false))
        } else {
            LoadMoreButtonViewHolder(ItemLoadMoreButtonBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val shoppingContent = shoppingTypes[position]
        if (holder is ProductViewHolder && shoppingContent is ShoppingType.ProductType) {
            holder.bind(shoppingContent.product, clickListener)
        }
        if (holder is LoadMoreButtonViewHolder) {
            holder.bind(clickListener)
        }
    }

    override fun getItemCount(): Int {
        return shoppingTypes.size
    }

    fun loadData(
        data: List<Product>,
        canLoadMore: Boolean,
    ) {
        val currentSize = if (shoppingTypes.isEmpty()) 0 else shoppingTypes.size - 1
        val items = data.subList(currentSize, data.size).map(ShoppingType::ProductType)

        shoppingTypes.removeLastOrNull()
        shoppingTypes += items

        if (canLoadMore) {
            shoppingTypes += ShoppingType.LoadMoreType()
            notifyItemRangeInserted(shoppingTypes.size, items.size + 1)
        } else {
            notifyItemRangeInserted(shoppingTypes.size, items.size)
        }
    }
}
