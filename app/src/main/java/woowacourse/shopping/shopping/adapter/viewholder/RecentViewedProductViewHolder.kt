package woowacourse.shopping.shopping.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentViewedProductBinding
import woowacourse.shopping.model.RecentViewedProductUiModel

class RecentViewedProductViewHolder private constructor(
    private val binding: ItemRecentViewedProductBinding,
    private val onProductImageClicked: (productId: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: RecentViewedProductUiModel) {
        binding.product = product
        binding.root.setOnClickListener {
            onProductImageClicked(product.id)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onProductImageClicked: (productId: Int) -> Unit,
        ): RecentViewedProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecentViewedProductBinding.inflate(layoutInflater, parent, false)

            return RecentViewedProductViewHolder(
                binding = binding,
                onProductImageClicked = onProductImageClicked
            )
        }
    }
}
