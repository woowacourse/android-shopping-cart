package woowacourse.shopping.view.shoppingmain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductMainBinding
import woowacourse.shopping.uimodel.ProductUIModel

class ProductViewHolder(
    parent: ViewGroup,
    private val productOnClick: (ProductUIModel) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_product_main, parent, false)
) {
    private val binding = ItemProductMainBinding.bind(itemView)
    private lateinit var product: ProductUIModel

    init {
        itemView.setOnClickListener {
            productOnClick(product)
        }
    }

    fun bind(item: ProductUIModel) {
        product = item
        binding.product = product
    }
}
