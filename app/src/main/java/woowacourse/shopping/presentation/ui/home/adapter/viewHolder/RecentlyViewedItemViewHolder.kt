package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewedProductBinding
import woowacourse.shopping.domain.model.Product

class RecentlyViewedItemViewHolder(
    private val binding: ItemRecentlyViewedProductBinding,
    private val clickProduct: (Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var product: Product? = null

    init {
        binding.root.setOnClickListener {
            product?.let { selectedProduct ->
                clickProduct(selectedProduct)
            }
        }
    }

    fun bind(data: Product) {
        product = data
        binding.product = data
    }

    companion object {
        fun getView(
            parent: ViewGroup,
            layoutInflater: LayoutInflater,
        ): ItemRecentlyViewedProductBinding =
            ItemRecentlyViewedProductBinding.inflate(layoutInflater, parent, false)
    }
}
