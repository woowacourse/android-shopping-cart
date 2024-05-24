package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductHistoryBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.productlist.ProductListActionHandler

class ProductHistoryListAdapter(
    private val actionHandler: ProductListActionHandler,
    private val productHistoryList: MutableList<Product> = mutableListOf(),
) : RecyclerView.Adapter<ProductHistoryListAdapter.ProductHistoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderProductHistoryBinding.inflate(inflater, parent, false)
        return ProductHistoryViewHolder(binding, actionHandler)
    }

    override fun getItemCount(): Int = productHistoryList.size

    override fun onBindViewHolder(
        holder: ProductHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(productHistoryList[position])
    }

    fun updateProductHistorys(newCartProductList: List<Product>) {
        productHistoryList.clear()
        productHistoryList.addAll(newCartProductList)
        notifyDataSetChanged()
    }

    class ProductHistoryViewHolder(
        private val binding: HolderProductHistoryBinding,
        private val actionHandler: ProductListActionHandler,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productHistory: Product) {
            binding.productHistory = productHistory
            binding.actionHandler = actionHandler
        }
    }
}
