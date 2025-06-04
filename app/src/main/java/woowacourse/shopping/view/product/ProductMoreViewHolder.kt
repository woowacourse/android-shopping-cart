package woowacourse.shopping.view.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductMoreBinding

class ProductMoreViewHolder(
    binding: ItemProductMoreBinding,
    productListener: ProductMoreListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.productMoreListener = productListener
    }

    companion object {
        fun of(
            parent: ViewGroup,
            productListener: ProductMoreListener,
        ): ProductMoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemProductMoreBinding.inflate(layoutInflater, parent, false)
            return ProductMoreViewHolder(binding, productListener)
        }
    }

    fun interface ProductMoreListener {
        fun onLoadClick()
    }
}
