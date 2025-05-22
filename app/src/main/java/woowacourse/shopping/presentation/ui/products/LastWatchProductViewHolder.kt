package woowacourse.shopping.presentation.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemLastProductBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.viewmodel.products.ProductsViewModel

class LastWatchProductViewHolder(
    private val binding: ItemLastProductBinding,
    private val onClick: ProductsAdapter.OnClickHandler,
    private val holderLifecycleOwner: LifecycleOwner,
    private val viewModel: ProductsViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Product) {
        binding.apply {
            product = item
            vm = viewModel
            lifecycleOwner = holderLifecycleOwner
            ivProductPreview.setOnClickListener { onClick.onProductClick(item.id) }
            executePendingBindings()
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClick: ProductsAdapter.OnClickHandler,
            lifecycleOwner: LifecycleOwner,
            viewModel: ProductsViewModel,
        ): LastWatchProductViewHolder {
            val binding =
                ItemLastProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            return LastWatchProductViewHolder(binding, onClick, lifecycleOwner, viewModel)
        }
    }
}
