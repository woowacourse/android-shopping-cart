package woowacourse.shopping.presentation.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.model.Product

class RecentAdapter(
    private val itemClickListener: ItemClickListener,
) : RecyclerView.Adapter<RecentAdapter.RecentProductViewHolder>() {
    private var items: List<Product> = emptyList()

    fun submitList(newList: List<Product>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentProductBinding.inflate(inflater, parent, false)
        return RecentProductViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RecentProductViewHolder(
        private val binding: ItemRecentProductBinding,
        private val itemClickListener: ItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.product = item
            binding.itemClickListener = itemClickListener
            binding.executePendingBindings()
        }
    }
}
