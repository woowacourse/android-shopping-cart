package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.model.ProductUiModel
import woowacourse.shopping.presentation.ui.home.adapter.ProductClickListener

class ProductViewHolder(
    private val binding: ItemProductBinding,
    productClickListener: ProductClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = productClickListener
    }

    fun bind(data: ProductUiModel) {
        binding.product = data
    }

    companion object {
        fun getView(parent: ViewGroup): ItemProductBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemProductBinding.inflate(layoutInflater, parent, false)
        }
    }
}
