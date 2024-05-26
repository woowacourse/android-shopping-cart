package woowacourse.shopping.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.presentation.action.CartItemCountHandler
import woowacourse.shopping.presentation.home.HomeActionHandler
import woowacourse.shopping.presentation.uistate.Order
import java.lang.IllegalArgumentException

class ProductAdapter(
    private val homeActionHandler: HomeActionHandler,
    private val cartItemCountHandler: CartItemCountHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val orders: MutableList<Order> = mutableListOf()
    private val ordersPosition: HashMap<Long, Int> = hashMapOf()
    private var isLoadingAvailable: Boolean = true

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_PRODUCT -> {
                val binding: ItemProductBinding =
                    ItemProductBinding.inflate(layoutInflater, parent, false)
                ProductViewHolder(binding, homeActionHandler, cartItemCountHandler)
            }

            TYPE_LOAD -> {
                val binding: ItemLoadMoreBinding =
                    ItemLoadMoreBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding, homeActionHandler)
            }

            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> {
                val order = orders[position]
                holder.bind(order)
                ordersPosition[order.product.id] = position
            }

            is LoadingViewHolder -> holder.bind(isLoadingAvailable)
            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    override fun getItemCount(): Int = orders.size + ADD_MORE_COUNT

    override fun getItemViewType(position: Int): Int {
        return if (position < orders.size) {
            TYPE_PRODUCT
        } else {
            TYPE_LOAD
        }
    }

    fun addProducts(insertedProducts: List<Order>) {
        val previousSize = orders.size
        orders.addAll(insertedProducts.subList(previousSize, insertedProducts.size))
        notifyItemRangeInserted(previousSize, insertedProducts.size - previousSize)
    }

    fun updateProduct(order: Order) {
        val position = ordersPosition[order.product.id]
        position?.let {
            orders[it] = order
            notifyItemChanged(it)
        }
    }

    fun updateLoadStatus(isLoadingAvailable: Boolean) {
        this.isLoadingAvailable = isLoadingAvailable
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = null
        recyclerView.setHasFixedSize(true)
    }

    companion object {
        const val TYPE_PRODUCT = 1000
        const val TYPE_LOAD = 1001
        private const val ADD_MORE_COUNT = 1
        private const val EXCEPTION_ILLEGAL_VIEW_TYPE = "유효하지 않은 뷰 타입입니다."
    }
}

@BindingAdapter("productThumbnail")
fun ImageView.setProductThumbnail(thumbnailUrl: String?) {
    Glide.with(context)
        .load(thumbnailUrl)
        .into(this)
}
