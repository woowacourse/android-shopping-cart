package woowacourse.shopping.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.productlist.uimodel.ProductListClickAction
import woowacourse.shopping.productlist.uimodel.RecentProductUiModel
import woowacourse.shopping.util.imageUrlToSrc

class RecentProductAdapter(
    private val onClick: ProductListClickAction,
) : RecyclerView.Adapter<RecentProductAdapter.RecentProductViewHolder>() {
    private val items: MutableList<RecentProductUiModel> = mutableListOf()

    class RecentProductViewHolder(
        private val binding: ItemRecentProductBinding,
        private val onClick: ProductListClickAction,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: RecentProductUiModel) {
            with(binding) {
                itemView.context.imageUrlToSrc(item.imageUrl, ivRecentProductItem)
                tvRecentProductName.text = item.name
                root.setOnClickListener {
                    onClick.onProductClicked(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val binding =
            ItemRecentProductBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return RecentProductViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        return holder.onBind(items[position])
    }

    fun replaceAll(products: List<RecentProductUiModel>) {
        items.clear()
        items.addAll(products)
        notifyItemRangeChanged(0, products.size)
    }
}
