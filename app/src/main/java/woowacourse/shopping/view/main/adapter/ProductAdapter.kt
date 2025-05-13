package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import kotlin.collections.mutableListOf

class ProductAdapter(
    private val items: MutableList<Product> = mutableListOf(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    fun submitList(newItems: List<Product>) {
        val lastPosition = newItems.size
        items.addAll(newItems)
        notifyItemRangeChanged(lastPosition, newItems.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemProductBinding.inflate(inflater)

        return ProductViewHodler(bind)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ProductViewHodler).bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
