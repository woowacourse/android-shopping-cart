package woowacourse.shopping.presentation.view.productlist.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductListBinding
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.view.custom.CountView

class ProductListViewHolder(
    parent: ViewGroup,
    onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from((parent.context))
        .inflate(R.layout.item_product_list, parent, false)
) {
    private val binding = ItemProductListBinding.bind(itemView)

    init {
        binding.clProductItem.setOnClickListener {
            onItemClick(bindingAdapterPosition)
        }
    }

    fun bind(product: ProductModel, onCountClick: (Long, Int) -> Unit) {
        binding.product = product
        binding.productCountView.setCount(product.count)
        binding.productCountView.countStateChangeListener =
            object : CountView.OnCountStateChangeListener {
                override fun onCountChanged(countView: CountView?, count: Int) {
                    onCountClick(product.id, count)
                }
            }
    }
}
