package woowacourse.shopping.presentation.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import woowacourse.shopping.databinding.LayoutLastProductsBinding
import woowacourse.shopping.presentation.viewmodel.products.ProductsViewModel
import woowacourse.shopping.domain.model.Product

class LastWatchProductsViewHolder(
    parent: ViewGroup,
    onClickHandler: ProductsAdapter.OnClickHandler,
    private val lifecycleOwner: LifecycleOwner,
    viewModel: ProductsViewModel,
) : ProductsItemViewHolder<ProductsItem.LastWatchProductsItem, LayoutLastProductsBinding>(
    LayoutLastProductsBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false,
    ),
) {
    private val nestedAdapter = LastWatchProductsAdapter(onClickHandler, lifecycleOwner, viewModel)

    init {
        binding.rvLastProducts.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            adapter = nestedAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun bind(item: ProductsItem.LastWatchProductsItem) {
        super.bind(item)
        binding.lifecycleOwner = lifecycleOwner
        nestedAdapter.updateProductItems(item.value)
    }
}