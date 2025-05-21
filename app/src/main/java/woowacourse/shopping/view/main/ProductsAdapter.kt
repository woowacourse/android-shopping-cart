package woowacourse.shopping.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.uimodel.MainRecyclerViewProduct

class ProductsAdapter(
    private val onProductSelected: (Product) -> Unit,
) : RecyclerView.Adapter<ProductsViewHolder>() {
    private var products: List<Product> = listOf()
    private var quantity: MutableMap<Product, MutableLiveData<Int>> = mutableMapOf()

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
    ) {
        val item = products[position]
        val quantityLiveData = quantity[item]
        holder.bind(item, quantityLiveData)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsViewHolder {
        val binding =
            ItemProductBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                ),
                parent,
                false,
            )
        return ProductsViewHolder(binding, onProductSelected)
    }

    fun updateProducts(mainRecyclerViewProduct: MainRecyclerViewProduct) {
        products += mainRecyclerViewProduct.page.items
        quantity += mainRecyclerViewProduct.quantityMap
        notifyItemInserted(itemCount)
    }
}
