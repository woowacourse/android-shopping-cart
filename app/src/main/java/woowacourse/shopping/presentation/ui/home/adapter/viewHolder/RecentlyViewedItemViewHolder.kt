package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewedProductBinding
import woowacourse.shopping.domain.model.Product

class RecentlyViewedItemViewHolder(
    private val binding: ItemRecentlyViewedProductBinding,
    private val clickProduct: (product: Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            clickProduct(bindingAdapterPosition)
        }
    }

    fun bind(data: Product) {
        binding.product = data
    }

    companion object {
        fun getView(parent: ViewGroup): ItemRecentlyViewedProductBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemRecentlyViewedProductBinding.inflate(
                layoutInflater,
                parent,
                false,
            )
        }
    }
}
