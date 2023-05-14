package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.presentation.model.ProductModel

class ProductListViewHolder(
    parent: ViewGroup,
    onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from((parent.context))
        .inflate(R.layout.item_product_list, parent, false)
) {
    private val binding = ItemProductListBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            onClick(bindingAdapterPosition)
        }
    }

    fun bind(product: ProductModel) {
        binding.product = product
        binding.tvProductTitle.text = product.title
        binding.tvProductPrice.text =
            binding.root.context.getString(R.string.product_price_format, product.price)
    }
}
