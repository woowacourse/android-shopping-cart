package woowacourse.shopping.view.product.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.product.OnProductListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    eventListener: OnProductListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onClick = eventListener
    }

    fun bind(product: Product) {
        binding.product = product
    }

    companion object {
        fun from(
            parent: ViewGroup,
            eventListener: OnProductListener,
        ): ProductViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            return ProductViewHolder(binding, eventListener)
        }
    }
}
