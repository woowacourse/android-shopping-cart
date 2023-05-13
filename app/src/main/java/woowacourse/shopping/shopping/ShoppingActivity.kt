package woowacourse.shopping.shopping

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
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.data.ProductFakeRepository
import woowacourse.shopping.data.ProductFakeRepository.KEY_PRODUCT_OFFSET
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.shopping.contract.ShoppingContract
import woowacourse.shopping.shopping.contract.presenter.ShoppingPresenter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        setSupportActionBar(binding.toolbar)
        presenter = ShoppingPresenter(
            this,
            savedInstanceState?.getInt(KEY_PRODUCT_OFFSET) ?: 20,
            ProductFakeRepository,
            RecentProductDatabase(this),
        )

        initLayoutManager()
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

    override fun onResume() {
        super.onResume()
        presenter.updateProducts()
    }

    private fun initLayoutManager() {
        val layoutManager = GridLayoutManager(this@ShoppingActivity, 2)
        val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val spanCount = 2

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (binding.productRecyclerview.adapter?.getItemViewType(position)) {
                    ProductsItemType.TYPE_FOOTER -> spanCount
                    ProductsItemType.TYPE_ITEM -> 1
                    else -> spanCount
                }
            }
        }

        binding.productRecyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State,
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
        binding.productRecyclerview.layoutManager = layoutManager
    }

    override fun setProducts(data: List<ProductsItemType>) {
        binding.productRecyclerview.adapter = ProductsAdapter(
            data,
            presenter::navigateToItemDetail,
            presenter::fetchMoreProducts,
        )
    }

    override fun navigateToProductDetail(product: ProductUIModel) {
        startActivity(ProductDetailActivity.from(this, product))
    }

    override fun addProducts(data: List<ProductsItemType>) {
        binding.productRecyclerview.adapter?.let {
            if (it is ProductsAdapter) {
                it.updateData(data)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PRODUCT_OFFSET, ProductFakeRepository.offset)
    }

    private fun navigateToCart() {
        startActivity(CartActivity.from(this))
    }
}
