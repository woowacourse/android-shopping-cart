package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductMoreBinding

class ProductMoreViewHolder(
    binding: ItemProductMoreBinding,
    productListener: ProductMoreClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productMoreClickListener = productListener
    }

    companion object {
        fun of(
            parent: ViewGroup,
            productListener: ProductMoreClickListener,
        ): ProductMoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductMoreBinding.inflate(layoutInflater, parent, false)
            return ProductMoreViewHolder(binding, productListener)
        }
    }

    fun interface ProductMoreClickListener {
        fun onLoadClick()
    }
}
