package woowacourse.shopping.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.presentation.ui.home.SetClickListener
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.Products
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.RecentlyViewedProducts
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.ShowMoreProducts
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.PRODUCT
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.RECENTLY_VIEWED
import woowacourse.shopping.presentation.ui.home.adapter.HomeViewType.SHOW_MORE
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.ProductViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.RecentlyViewedViewHolder
import woowacourse.shopping.presentation.ui.home.adapter.viewHolder.ShowMoreViewHolder
import woowacourse.shopping.presentation.ui.home.uiModel.ProductInCartUiState

class HomeAdapter(
    productInCart: List<ProductInCartUiState>,
    viewItems: List<ProductsByView>,
    private val onClick: SetClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var layoutInflater: LayoutInflater
    private val _viewItems: MutableList<ProductsByView> = viewItems.toMutableList()
    private val _productInCart: MutableList<ProductInCartUiState> = productInCart.toMutableList()

    override fun getItemViewType(position: Int): Int {
        return when (_viewItems[position]) {
            is Products -> PRODUCT.ordinal
            is RecentlyViewedProducts -> RECENTLY_VIEWED.ordinal
            is ShowMoreProducts -> SHOW_MORE.ordinal
        }
    }

    override fun getItemCount(): Int = _viewItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)

        return when (HomeViewType.valueOf(viewType)) {
            PRODUCT -> initProductViewHolder(parent)
            RECENTLY_VIEWED -> initRecentlyViewedViewHolder(parent)
            SHOW_MORE -> initShowMoreViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                holder.bind(
                    _viewItems[position] as Products,
                    _productInCart,
                )
            }

            is RecentlyViewedViewHolder -> holder.bind(_viewItems[position] as RecentlyViewedProducts)
        }
    }

    private fun initProductViewHolder(parent: ViewGroup) =
        ProductViewHolder(
            ProductViewHolder.getView(parent, layoutInflater),
            onClick,
        )

    private fun initRecentlyViewedViewHolder(parent: ViewGroup) =
        RecentlyViewedViewHolder(RecentlyViewedViewHolder.getView(parent, layoutInflater), onClick)

    private fun initShowMoreViewHolder(parent: ViewGroup) =
        ShowMoreViewHolder(
            ShowMoreViewHolder.getView(parent, layoutInflater),
            onClick::setClickEventOnShowMoreButton,
        )

    fun addProducts(products: List<ProductsByView>) {
        val lastIndex = _viewItems.lastIndex
        val productsCount = products.size
        _viewItems.addAll(lastIndex, products)

        notifyItemRangeInserted(lastIndex, productsCount)
    }

    fun addCountOfProductInCart(productInCart: List<ProductInCartUiState>) {
        _productInCart.clear()
        _productInCart.addAll(productInCart)

        notifyDataSetChanged()
    }

    sealed interface ProductsByView {
        data class Products(val product: Product) : ProductsByView
        data class RecentlyViewedProducts(val recentProduct: List<Product>) : ProductsByView
        object ShowMoreProducts : ProductsByView
    }
}
