package woowacourse.shopping.presentation.shopping.product.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.presentation.common.ItemUpdateHelper
import woowacourse.shopping.presentation.shopping.detail.ProductUi
import woowacourse.shopping.presentation.shopping.product.RecentProductItemListener

class RecentProductAdapter(
    private val listener: RecentProductItemListener,
) :
    RecyclerView.Adapter<RecentProductAdapter.RecentProductViewHolder>() {
    private var products: List<ProductUi> = emptyList()
    private val updateHelper: ItemUpdateHelper<ProductUi> =
        ItemUpdateHelper<ProductUi>(
            adapter = this,
            areItemsTheSame = { oldItem, newItem ->
                (oldItem.id == newItem.id)
            },
            areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
        )

    override fun getItemCount(): Int = products.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecentProductViewHolder(
            ItemRecentProductBinding.inflate(layoutInflater, parent, false),
            listener,
        )
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(products[position])
    }

    fun updateProducts(newProducts: List<ProductUi>) {
        val oldProducts = products.toList()
        products = newProducts
        updateHelper.update(oldProducts, newProducts)
    }

    class RecentProductViewHolder(
        private val binding: ItemRecentProductBinding,
        private val listener: RecentProductItemListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUi) {
            binding.product = product
            binding.listener = listener
        }
    }
}
