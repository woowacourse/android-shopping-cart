package woowacourse.shopping.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartBinding
import woowacourse.shopping.db.Product

class CartItemRecyclerViewAdapter(
    private var values: List<Product>,
    private val onClick: (id: Int) -> Unit,
) : RecyclerView.Adapter<CartItemRecyclerViewAdapter.ViewHolder>() {
    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.itemAnimator = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            HolderCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClick = { id -> onClick(id) },
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size.coerceAtMost(COUNT_PER_PAGE)

    fun updateData(newData: List<Product>) {
        Log.d(TAG, "updateData: ")
        this.values = newData

        if (newData.isEmpty()) {
            notifyItemRemoved(0)
            return
        }
        notifyItemRangeChanged(0, itemCount)
    }

    inner class ViewHolder(
        private val binding: HolderCartBinding,
        private val onClick: (id: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.cartProductDelete.setOnClickListener { onClick(product.id) }
        }
    }

    companion object {
        private const val TAG = "CartItemRecyclerViewAdapter"
        private const val COUNT_PER_PAGE = 5
    }
}
