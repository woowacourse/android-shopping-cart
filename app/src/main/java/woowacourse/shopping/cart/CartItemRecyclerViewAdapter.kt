package woowacourse.shopping.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCartBinding
import woowacourse.shopping.db.Product

class CartItemRecyclerViewAdapter(
    private var values: List<Product>,
    private val onClick: (id: Int) -> Unit,
) : RecyclerView.Adapter<CartItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            HolderCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = { id -> onClick(id) }
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size.coerceAtMost(5)

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Product>) {
        this.values = newData
        notifyDataSetChanged()
    }


    inner class ViewHolder(
        private val binding: HolderCartBinding,
        private val onClick: (id: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
            binding.cartProductDelete.setOnClickListener { onClick(product.id) }
        }
    }

}
