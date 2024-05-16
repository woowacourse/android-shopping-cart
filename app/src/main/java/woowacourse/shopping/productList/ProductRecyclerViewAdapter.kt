package woowacourse.shopping.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderProductBinding
import woowacourse.shopping.db.Product

class ProductRecyclerViewAdapter(
    private var values: List<Product>,
    private val onClick: (id: Int) -> Unit,
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClick = { id -> onClick(id) }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size


    fun updateData(newData: List<Product>) {
        this.values = newData
        notifyDataSetChanged()
    }


    inner class ViewHolder(
        private val binding: HolderProductBinding,
        private val onClick: (id: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.root.setOnClickListener { onClick(product.id) }
        }
    }
}
