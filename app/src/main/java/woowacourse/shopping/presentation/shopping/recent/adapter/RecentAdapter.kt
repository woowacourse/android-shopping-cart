package woowacourse.shopping.presentation.shopping.recent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.presentation.util.ItemUpdateHelper

class RecentAdapter(private val onClickItem: (id: Long) -> Unit) :
    RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {
    private var products: List<RecentProduct> = emptyList()
    private val updateHelper: ItemUpdateHelper<RecentProduct> =
        ItemUpdateHelper<RecentProduct>(
            adapter = this,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.product.id == newItem.product.id
            },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
        )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentProductBinding.inflate(layoutInflater, parent, false)
        return RecentViewHolder(binding, onClickItem)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: RecentViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateProducts(newProducts: List<RecentProduct>) {
        val oldProducts = products.toList()
        this.products = newProducts
        updateHelper.update(oldProducts, newProducts)
    }

    class RecentViewHolder(
        private val binding: ItemRecentProductBinding,
        private val onClickItem: (id: Long) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: RecentProduct) {
            binding.recent = product
            binding.root.setOnClickListener { onClickItem(product.product.id) }
        }
    }
}
