package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductUiModel

class ProductViewHolder(
    view: ViewGroup,
) : RecyclerView.ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(view.context), view, false).root) {
    private val binding = ItemProductBinding.bind(itemView)

    fun bind(product: ProductUiModel) {
        binding.product = product
    }
}
