package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products

class ProductViewHolder(
    private val binding: ItemProductBinding,
    private val clickProduct: (product: Product) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var product: Product? = null

    init {
        binding.root.setOnClickListener {
            product?.let { selectedProduct ->
                clickProduct(selectedProduct)
            }
        }
    }

    fun bind(data: Products) {
        product = data.product
        binding.product = data.product
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemProductBinding =
            ItemProductBinding.inflate(layoutInflater, parent, false)
    }
}
