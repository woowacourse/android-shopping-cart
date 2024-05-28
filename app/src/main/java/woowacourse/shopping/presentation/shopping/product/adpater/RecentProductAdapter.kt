package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.product.RecentProductItemListener
import woowacourse.shopping.presentation.util.ItemDiffCallback

class RecentProductAdapter(
    private val listener: RecentProductItemListener,
) :
    ListAdapter<ProductUi, RecentProductAdapter.RecentProductViewHolder>(productComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecentProductViewHolder(
            ItemRecentProductBinding.inflate(layoutInflater, parent, false),
            listener,
        )
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(currentList[position])
    }

    class RecentProductViewHolder(
        private val binding: ItemRecentProductBinding,
        private val listener: RecentProductItemListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUi) {
            binding.product = product
            binding.listener = listener
        }
    }

    companion object {
        private val productComparator =
            ItemDiffCallback<ProductUi>(
                onItemsTheSame = { old, new ->
                    old.id == new.id
                },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
