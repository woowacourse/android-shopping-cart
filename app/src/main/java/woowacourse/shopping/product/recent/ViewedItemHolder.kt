package woowacourse.shopping.product.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ViewedItemBinding
import woowacourse.shopping.product.catalog.ProductUiModel

class ViewedItemHolder(
    private val binding: ViewedItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewedProduct: ProductUiModel) {
        binding.product = viewedProduct
    }

    companion object {
        fun from(parent: ViewGroup): ViewedItemHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ViewedItemBinding.inflate(inflater, parent, false)

            return ViewedItemHolder(binding)
        }
    }
}
