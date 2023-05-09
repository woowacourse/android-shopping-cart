package woowacourse.shopping.shopping

import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.databinding.ProductItemBinding

class ProductsViewHolder(private val binding: ProductItemBinding, val onClickItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(productUIModel: ProductUIModel) {
        binding.product = productUIModel
    }
}
