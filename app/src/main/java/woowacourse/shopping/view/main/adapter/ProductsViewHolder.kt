package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.main.ProductsViewModel

class ProductsViewHolder(
    private val parent: ViewGroup,
    private val viewModel: ProductsViewModel,
    private val binding: ItemProductBinding = inflate(parent),
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.handler = ProductEventHandlerImpl(parent.context, binding, viewModel)
    }

    fun bind(
        item: Product,
        quantityLiveData: MutableLiveData<Int>,
    ) {
        binding.product = item
        binding.quantity = quantityLiveData
    }

    companion object {
        fun inflate(parent: ViewGroup): ItemProductBinding {
            return ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        }
    }
}
