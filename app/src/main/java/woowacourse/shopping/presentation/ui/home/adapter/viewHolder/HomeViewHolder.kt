package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.model.Product

class HomeViewHolder(
    private val binding: ItemProductBinding,
    private val clickProduct: (productId: Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            clickProduct(bindingAdapterPosition)
        }
    }

    fun bind(data: Product) {
        binding.product = data
    }

    companion object {
        fun getView(parent: ViewGroup): ItemProductBinding {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ItemProductBinding.inflate(layoutInflater, parent, false)
        }
    }
}
