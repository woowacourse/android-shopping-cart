package woowacourse.shopping.ui.shopping

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.ProductFakeRepository
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.product.ProductDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.detailedProduct.DetailedProductActivity
import woowacourse.shopping.ui.shopping.productAdapter.ProductsAdapter
import woowacourse.shopping.ui.shopping.productAdapter.ProductsItemType
import woowacourse.shopping.ui.shopping.productAdapter.ProductsListener

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initToolbar()
        initPresenter()
        initLayoutManager()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateProducts()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun initPresenter() {
        ProductFakeRepository.getAll().forEach {
            ProductDatabase(this).insert(it)
        }
        presenter = ShoppingPresenter(
            this,
            ProductDatabase(this),
            RecentProductDatabase(this),
            CartDatabase(this)
        )
        presenter.setUpProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> navigateToCart()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun initLayoutManager() {
        val layoutManager = GridLayoutManager(this@ShoppingActivity, 2)
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val spanCount = 2

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (binding.rvProducts.adapter?.getItemViewType(position)) {
                    ProductsItemType.TYPE_FOOTER -> spanCount
                    ProductsItemType.TYPE_ITEM -> 1
                    else -> spanCount
                }
            }
        }

        binding.rvProducts.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val spanSize = layoutManager.spanSizeLookup.getSpanSize(position)

                if (spanSize != spanCount) {
                    outRect.left = spacing
                    outRect.right = spacing
                }

                outRect.top = spacing
            }
        })
        binding.rvProducts.layoutManager = layoutManager
    }

    override fun setProducts(data: List<ProductsItemType>) {
        val listener = object : ProductsListener {
            override fun onClickItem(productId: Int) { presenter.navigateToItemDetail(productId) }
            override fun onReadMoreClick() { presenter.fetchMoreProducts() }
            override fun onAddCartOrUpdateCount(productId: Int, count: Int, block: () -> Unit) {
                binding.rvProducts.adapter.let { adapter ->
                    if (adapter is ProductsAdapter) { adapter.updateItemCount(productId, count) }
                }
                block()
                presenter.updateItem(productId, count)
            }
        }
        binding.rvProducts.adapter = ProductsAdapter(data.toMutableList(), listener)
    }

    override fun updateProducts(data: List<ProductsItemType>) {
        binding.rvProducts.adapter?.let {
            if (it is ProductsAdapter) {
                it.submitList(data)
            }
        }
    }

    override fun navigateToProductDetail(product: ProductUIModel) {
        startActivity(DetailedProductActivity.getIntent(this, product))
    }

    private fun navigateToCart() {
        startActivity(CartActivity.getIntent(this))
    }
}
