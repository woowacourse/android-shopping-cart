package woowacourse.shopping.presentation.products.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.products.ProductsViewModel

@SuppressLint("NotifyDataSetChanged")
class LastWatchProductsAdapter(
    private val onClickHandler: ProductsAdapter.OnClickHandler,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: ProductsViewModel,
) : RecyclerView.Adapter<LastWatchProductViewHolder>() {
    private val productsItems: MutableList<Product> = mutableListOf<Product>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LastWatchProductViewHolder =
        LastWatchProductViewHolder.from(
            parent,
            onClickHandler,
            lifecycleOwner,
            viewModel,
        )

    override fun onBindViewHolder(
        holder: LastWatchProductViewHolder,
        position: Int,
    ) {
        val productsItem = productsItems[position]
        holder.bind(productsItem)
    }

    override fun getItemCount(): Int = productsItems.size

    fun updateProductItems(newItems: List<Product>) {
        productsItems.clear()
        productsItems.addAll(newItems)
        notifyDataSetChanged()
    }
}
