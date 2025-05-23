package woowacourse.shopping.product.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ProductItemBinding

class ProductViewHolder(
    private val binding: ProductItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: ProductUiModel) {
        val quantityControlBar = binding.layoutQuantityControlBar.linearLayoutQuantityControlBar
        val fabBtn = binding.layoutQuantityControlBar.fabQuantityAdd

        binding.product = product
        binding.layoutQuantityControlBar.product = product

        if (product.quantity == 0) {
            quantityControlBar.visibility = View.INVISIBLE
            fabBtn.visibility = View.VISIBLE
        } else {
            quantityControlBar.visibility = View.VISIBLE
            fabBtn.visibility = View.INVISIBLE
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            productActionListener: ProductActionListener,
            quantityControlListener: QuantityControlListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProductItemBinding.inflate(inflater, parent, false)
            binding.productActionListener = productActionListener
            binding.layoutQuantityControlBar.quantityControlListener = quantityControlListener
            return ProductViewHolder(binding)
        }
    }
}
