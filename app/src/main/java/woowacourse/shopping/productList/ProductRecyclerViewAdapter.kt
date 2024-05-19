package woowacourse.shopping.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.cart.OnProductItemClickListener
import woowacourse.shopping.data.Product
import woowacourse.shopping.databinding.HolderProductBinding

class ProductRecyclerViewAdapter(
    private var values: List<Product>,
    private val onProductItemClickListener: OnProductItemClickListener,
) : RecyclerView.Adapter<ProductsItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsItemViewHolder = ProductsItemViewHolder(
        HolderProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) { onProductItemClickListener.onClick(it) }

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder,
        position: Int,
    ) = holder.bind(values[position])

    override fun getItemCount(): Int = values.size

    fun updateData(newData: List<Product>) {
        this.values = newData
        notifyItemRangeInserted(values.size, newData.size)
    }
}
