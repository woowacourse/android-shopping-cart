package woowacourse.shopping.ui.fashionlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemRecentLayoutBinding
import woowacourse.shopping.databinding.LoadMoreItemBinding
import woowacourse.shopping.databinding.ProductItemBinding
import woowacourse.shopping.domain.product.CartItem

class FashionProductListAdapter(
    private val viewModel: ProductListViewModel,
    private val productClickListener: ProductClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ProductListViewType> = emptyList()
    private var cartItemsMap: Map<Long, CartItem> = emptyMap()

    override fun getItemViewType(position: Int): Int {
        val item = viewModel.products.value?.get(position) ?: throw IllegalArgumentException("")
        return when (item) {
            is ProductListViewType.FashionProductItemType -> R.layout.product_item
            is ProductListViewType.LoadMoreType -> R.layout.load_more_item
            is ProductListViewType.RecentProducts -> R.layout.item_recent_layout
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.product_item -> {
                val binding: ProductItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.product_item, parent, false)
                FashionProductItemViewHolder(binding, viewModel, productClickListener)
            }

            R.layout.load_more_item -> {
                val binding: LoadMoreItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.load_more_item, parent, false)
                LoadMoreViewHolder(binding, viewModel)
            }

            R.layout.item_recent_layout -> {
                val binding: ItemRecentLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.item_recent_layout, parent, false)
                RecentProductLayoutViewHolder(binding)
            }

            else -> throw IllegalArgumentException("지원하지 않는 타입입니다.")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = viewModel.products.value?.get(position) ?: throw IllegalArgumentException("")
        when (holder) {
            is FashionProductItemViewHolder -> {
                val product = item as ProductListViewType.FashionProductItemType
                val cartItem = cartItemsMap[item.product.id]
                holder.bind(product, cartItem)
            }
            is RecentProductLayoutViewHolder -> holder.bind((item as ProductListViewType.RecentProducts).products)
        }
    }

    override fun getItemCount() = viewModel.products.value?.size ?: throw IllegalArgumentException("")

    @SuppressLint("NotifyDataSetChanged")
    fun update(it: List<ProductListViewType>?) {
        items = it ?: throw IllegalArgumentException("")
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCartItems(cartMap: Map<Long, CartItem>) {
        this.cartItemsMap = cartMap
        notifyDataSetChanged()
    }
}
