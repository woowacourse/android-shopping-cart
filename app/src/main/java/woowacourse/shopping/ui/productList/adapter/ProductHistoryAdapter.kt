package woowacourse.shopping.ui.productList.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductHistoryBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.OnProductNavigator
import woowacourse.shopping.ui.productList.viewholder.ProductHistoryItemViewHolder

class ProductHistoryAdapter(
    private val navigator: OnProductNavigator
) : RecyclerView.Adapter<ProductHistoryItemViewHolder>() {
    private var products: List<Product> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductHistoryItemViewHolder =
        ProductHistoryItemViewHolder(
            HolderProductHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            navigator
        )

    override fun onBindViewHolder(
        holder: ProductHistoryItemViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    // TODO: 최적화
    @SuppressLint("NotifyDataSetChanged")
    fun update(newData: List<Product>) {
        this.products = newData.reversed()
        notifyDataSetChanged()
    }
}
