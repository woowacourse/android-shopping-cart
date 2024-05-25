package woowacourse.shopping.feature.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.cart.adapter.OnClickMinusButton
import woowacourse.shopping.feature.cart.adapter.OnClickPlusButton
import woowacourse.shopping.model.CartItemQuantity
import woowacourse.shopping.model.Product

class ProductAdapter(
    private val onClickProductItem: OnClickProductItem,
    private val onClickPlusButton: OnClickPlusButton,
    private val onClickMinusButton: OnClickMinusButton,
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val products: MutableList<Product> = mutableListOf()
    private val quantities: MutableList<CartItemQuantity> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
    ) {
        val product = products[position]
        val quantity = quantities[product.id.toInt()]
        // TODO: 앱 회전 또는 앱 다시 켰을 때 ui quantity 업데이트 되는 오류 해결하기
        Log.d("수량", "BindViewHolder ${quantity.quantity.count}")
        holder.bind(onClickProductItem, onClickPlusButton, onClickMinusButton, product, quantity) {
            updateQuantity(
                position,
            )
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateProducts(
        newProducts: List<Product>,
        positionStart: Int,
        itemCount: Int,
    ) {
        products.clear()
        products.addAll(newProducts)
        notifyItemRangeChanged(positionStart, itemCount)
    }

    fun updateQuantities(
        newQuantities: List<CartItemQuantity>,
        positionStart: Int,
        itemCount: Int,
    ) {
        Log.d("수량", "adapter update")
        quantities.addAll(newQuantities)
        Log.d("수량", "adapter ${newQuantities.map { it.quantity.count }}")
        notifyItemRangeChanged(positionStart, itemCount)
    }

    private fun updateQuantity(position: Int) {
        notifyItemChanged(position)
    }
}
