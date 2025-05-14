package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product

class ProductAdapter(
    private var items: List<Product>,
    private val handler: ProductsAdapterEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun submitList(newItems: List<Product>) {
        val lastPosition = items.size
        val subList = newItems.subList(lastPosition, newItems.size)
        items = items + subList
        notifyItemRangeInserted(lastPosition, newItems.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemProductBinding.inflate(inflater)

        return ProductViewHodler(bind, handler)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ProductViewHodler).bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
