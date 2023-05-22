package woowacourse.shopping.ui.shopping

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.data.MockProductWebServer
import woowacourse.shopping.data.ProductFakeRepository
import woowacourse.shopping.database.cart.CartDBHelper
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.shopping.contract.ShoppingContract
import woowacourse.shopping.ui.shopping.contract.presenter.ShoppingPresenter
import woowacourse.shopping.ui.shopping.viewHolder.ProductsOnClickListener
import woowacourse.shopping.utils.CustomViewOnClickListener

class ShoppingActivity :
    AppCompatActivity(),
    ShoppingContract.View,
    ProductsOnClickListener,
    CustomViewOnClickListener {
    private lateinit var mockWebServer: MockProductWebServer
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter

    private var cartSize: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMockWebServer()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        setSupportActionBar(binding.toolbar)

        presenter = ShoppingPresenter(
            this,
            ProductFakeRepository(mockWebServer.url),
            RecentProductDatabase(this),
            CartDatabase(
                CartDBHelper(this).writableDatabase,
            ),
        )

        initLayoutManager()
        presenter.setUpProducts()
    }

    private fun setMockWebServer() {
        val thread = Thread { mockWebServer = MockProductWebServer() }
        thread.start()
        thread.join()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        val actionView = menu?.findItem(R.id.cart)?.actionView
        actionView?.findViewById<ImageView>(R.id.cartBtn)?.setOnClickListener { navigateToCart() }
        actionView?.findViewById<TextView>(R.id.cartSize)?.let { cartSize = it }
        presenter.updateCountSize()
        return true
    }

    override fun onResume() {
        super.onResume()
        presenter.updateItemCounts()
        presenter.updateProducts()
        presenter.updateCountSize()
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
            ) { // 1부터 시작
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
            presenter,
            this,
            this,
            presenter::fetchMoreProducts,
        )
    }

    override fun navigateToProductDetail(product: ProductUIModel) {
        startActivity(ProductDetailActivity.from(this, product, true))
    }

    override fun addProducts(data: List<ProductsItemType>) {
        binding.productRecyclerview.adapter?.let {
            if (it is ProductsAdapter) {
                it.updateData(data)
            }
        }
    }

    override fun showCountSize(size: Int) {
        cartSize?.text = size.toString()
    }

    private fun navigateToCart() {
        startActivity(CartActivity.from(this))
    }

    override fun onClick(id: Long) {
        presenter.navigateToItemDetail(id)
    }

    override fun onAddCart(id: Long, count: Int) {
        binding.productRecyclerview.adapter?.let {
            if (it is ProductsAdapter) {
                it.updateItemCount(id, count)
            }
        }
        presenter.updateItemCount(id, count)
    }

    override fun increaseCount(id: Long) {
        presenter.increaseCount(id)
    }

    override fun decreaseCount(id: Long) {
        presenter.decreaseCount(id)
    }
}
