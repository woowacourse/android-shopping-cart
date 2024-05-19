package woowacourse.shopping.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.OnProductItemClickListener
import woowacourse.shopping.data.Product
import woowacourse.shopping.databinding.HolderCartBinding

class CartItemRecyclerViewAdapter(
    private var values: List<Product>,
    private val onProductItemClickListener: OnProductItemClickListener,
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {
    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.itemAnimator = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartItemViewHolder =
        ShoppingCartItemViewHolder(
            HolderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        ) { onProductItemClickListener.onClick(it) }

    override fun onBindViewHolder(
        holder: ShoppingCartItemViewHolder,
        position: Int,
    ) = holder.bind(values[position])

    override fun getItemCount(): Int = values.size.coerceAtMost(COUNT_PER_PAGE)

    fun updateData(newData: List<Product>) {
        this.values = newData

        if (newData.isEmpty()) {
            notifyItemRemoved(0)
            return
        }
        notifyItemRangeChanged(0, itemCount)
    }

    companion object {
        private const val COUNT_PER_PAGE = 5
    }
}
