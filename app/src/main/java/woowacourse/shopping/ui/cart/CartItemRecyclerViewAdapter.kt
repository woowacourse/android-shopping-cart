package woowacourse.shopping.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartBinding
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.ui.OnItemQuantityChangeListener
import woowacourse.shopping.ui.OnProductItemClickListener

class CartItemRecyclerViewAdapter(
    private val onProductItemClickListener: OnProductItemClickListener,
    private val onItemQuantityChangeListener: OnItemQuantityChangeListener,
) : RecyclerView.Adapter<ShoppingCartItemViewHolder>() {
    private var products: List<Product> = emptyList()

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
            onProductItemClickListener,
            onItemQuantityChangeListener,
        )

    override fun onBindViewHolder(
        holder: ShoppingCartItemViewHolder,
        position: Int,
    ) = holder.bind(products[position])

    override fun getItemCount(): Int = products.size

    fun updateData(newData: List<Product>) {
        val oldSize = products.size
        this.products = newData.toList()
        if (newData.isEmpty()) {
            notifyItemRangeRemoved(0, oldSize)
            return
        }

        notifyItemRangeChanged(0, itemCount)
    }
}
