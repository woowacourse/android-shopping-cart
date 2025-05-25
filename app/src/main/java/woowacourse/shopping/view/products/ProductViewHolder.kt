package woowacourse.shopping.view.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productClickListener: ProductsClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productClickListener = productClickListener

        binding.btnFloatingPlus.setOnClickListener {
            val product = binding.product ?: return@setOnClickListener
            productClickListener.onAddClick(product, 1)
        }

        binding.quantityControl.tvMinus.setOnClickListener {
            val product = binding.product ?: return@setOnClickListener
            productClickListener.onAddClick(product, -1)
        }

        binding.quantityControl.tvPlus.setOnClickListener {
            val product = binding.product ?: return@setOnClickListener
            productClickListener.onAddClick(product, 1)
        }
    }

    fun bind(
        item: ProductListViewType.ProductType,
        quantity: Int,
    ) {
        binding.product = item.product
        updateQuantityUI(quantity)
    }

    private fun updateQuantityUI(quantity: Int) {
        if (quantity == 0) {
            binding.btnFloatingPlus.visibility = View.VISIBLE
            binding.quantityControl.root.visibility = View.GONE
        } else {
            binding.btnFloatingPlus.visibility = View.GONE
            binding.quantityControl.root.visibility = View.VISIBLE
            binding.quantityControl.tvQuantity.text = quantity.toString()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            productClickListener: ProductsClickListener,
        ): ProductViewHolder {
            val binding =
                ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding, productClickListener)
        }
    }
}
