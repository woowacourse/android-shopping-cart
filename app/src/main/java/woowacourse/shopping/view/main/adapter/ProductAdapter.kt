package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.view.main.event.ProductsAdapterEventHandler

class ProductAdapter(
    private var items: List<Cart> = listOf(),
    private val handler: ProductsAdapterEventHandler,
) : RecyclerView.Adapter<ProductViewHolder>() {
    fun submitList(newItems: List<Cart>) {
        val lastPosition = items.size
        val subList = newItems.subList(lastPosition, newItems.size)
        items = items + subList
        notifyItemRangeInserted(lastPosition, newItems.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemProductBinding.inflate(inflater, parent, false)

        return ProductViewHolder(bind, handler)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
