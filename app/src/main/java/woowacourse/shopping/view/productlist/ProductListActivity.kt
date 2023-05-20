package woowacourse.shopping.view.productlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDbRepository
import woowacourse.shopping.data.ProductMockRepository
import woowacourse.shopping.data.RecentViewedDbRepository
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.view.cart.CartActivity
import woowacourse.shopping.view.productdetail.ProductDetailActivity

class ProductListActivity : AppCompatActivity(), ProductListContract.View {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var presenter: ProductListContract.Presenter
    private lateinit var cartCountInAppBar: TextView
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val id = it.data?.getIntExtra(ID, -1)
        when (it.resultCode) {
            RESULT_VIEWED -> {
                presenter.updateRecentViewed(id ?: -1)
            }
            RESULT_ADDED -> {
                presenter.updateRecentViewed(id ?: -1)
                presenter.fetchCartCount()
                presenter.fetchProductCount(id ?: -1)
                showToastAddInCart()
            }
            RESULT_VISIT_CART -> {
                presenter.fetchCartCount()
                presenter.fetchProductCounts()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setContentView(binding.root)
        setUpPresenter()
        setUpActionBar()
        presenter.fetchProducts()
    }

    private fun setUpBinding() {
        binding = ActivityProductListBinding.inflate(layoutInflater)
    }

    private fun setUpPresenter() {
        presenter =
            ProductListPresenter(this, ProductMockRepository, RecentViewedDbRepository(this), CartDbRepository(this))
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun showProducts(items: List<ProductListViewItem>) {
        val gridLayoutManager = GridLayoutManagerWrapper(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val isHeader = items[position].type == ProductListViewType.RECENT_VIEWED_ITEM
                val isFooter = items[position].type == ProductListViewType.SHOW_MORE_ITEM
                return if (isHeader || isFooter) {
                    HEADER_FOOTER_SPAN
                } else {
                    PRODUCT_ITEM_SPAN
                }
            }
        }
        binding.gridProducts.layoutManager = gridLayoutManager
        binding.gridProducts.adapter = ProductListAdapter(
            items,
            object : ProductListAdapter.OnItemClick {
                override fun onProductClick(product: ProductModel) {
                    presenter.showProductDetail(product)
                }

                override fun onShowMoreClick() {
                    presenter.showMoreProducts()
                }

                override fun onProductClickAddFirst(id: Int, count: Int) {
                    presenter.addToCartProducts(id, count)
                    presenter.fetchCartCount()
                }

                override fun onProductUpdateCount(id: Int, count: Int) {
                    presenter.updateCartProductCount(id, count)
                }
            },
        )
    }

    override fun notifyAddProducts(position: Int, size: Int) {
        binding.gridProducts.adapter?.notifyItemRangeInserted(position, size)
    }

    override fun notifyRecentViewedChanged() {
        binding.gridProducts.adapter?.notifyItemChanged(0)
    }

    override fun notifyDataChanged(position: Int) {
        binding.gridProducts.adapter?.notifyItemChanged(position)
    }

    override fun onClickProductDetail(product: ProductModel, lastViewedProduct: ProductModel) {
        val intent = ProductDetailActivity.newIntent(binding.root.context, product, lastViewedProduct)
        resultLauncher.launch(intent)
    }

    override fun showCartCount(count: Int) {
        if (count == 0) {
            cartCountInAppBar.visibility = View.GONE
            return
        }
        cartCountInAppBar.text = count.toString()
        cartCountInAppBar.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        val itemActionView = menu?.findItem(R.id.cart)?.actionView
        if (itemActionView != null) {
            cartCountInAppBar = itemActionView.findViewById(R.id.text_cart_count)
            presenter.fetchCartCount()

            val imageButton = itemActionView.findViewById<ImageButton>(R.id.btn_cart)
            imageButton?.setOnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showToastAddInCart() {
        Toast.makeText(this, ADD_IN_CART_MESSAGE, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val ADD_IN_CART_MESSAGE = "상품이 담겼습니다. 장바구니를 확인해주세요."
        private const val HEADER_FOOTER_SPAN = 2
        private const val PRODUCT_ITEM_SPAN = 1
        const val RESULT_VIEWED = 200
        const val RESULT_ADDED = 300
        const val RESULT_VISIT_CART = 400
        const val ID = "id"
    }
}
