package woowacourse.shopping.shopping

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.cart.CartActivity
import woowacourse.shopping.data.ProductFakeRepository.KEY_PRODUCT_OFFSET
import woowacourse.shopping.data.ProductRepository
import woowacourse.shopping.database.cart.CartDBHelper
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.recentProduct.RecentProductDatabase
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.productdetail.ProductDetailActivity.Companion.ACTIVITY_RESULT_CODE
import woowacourse.shopping.shopping.contract.ShoppingContract
import woowacourse.shopping.shopping.contract.presenter.ShoppingPresenter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {
    private lateinit var binding: ActivityShoppingBinding
    private lateinit var presenter: ShoppingContract.Presenter
    private val getResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                ACTIVITY_RESULT_CODE -> {
                    presenter.updateRecentProducts()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)
        presenter = ShoppingPresenter(
            this,
            savedInstanceState?.getInt(KEY_PRODUCT_OFFSET) ?: 20,
            ProductRepository(),
            RecentProductDatabase(this),
            CartDatabase(CartDBHelper(this).writableDatabase),
        )
        binding.cartIcon.setOnClickListener { navigateToCart() }
        initLayoutManager()
        presenter.setUpProducts()
    }

    override fun onResume() {
        super.onResume()
        presenter.setUpCarts()
        presenter.setUpProducts()
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

        binding.productRecyclerview.addItemDecoration(
            ProductRecyclerViewItemDecoration(
                layoutManager,
                spacing,
                spanCount,
            ),
        )
        binding.productRecyclerview.layoutManager = layoutManager
    }

    override fun setProducts(data: List<ProductsItemType>) {
        binding.productRecyclerview.adapter = ProductsAdapter(
            data,
            presenter::navigateToItemDetail,
            presenter::fetchMoreProducts,
            presenter::addCart,
            presenter::addOneInCart,
            presenter::removeOneInCart,
        )
    }

    override fun navigateToProductDetail(product: ProductUIModel) {
        getResult.launch(ProductDetailActivity.from(this, product))
    }

    override fun updateProducts(start: Int, products: List<ProductsItemType>) {
        updateProductsAdapterItem { updateProducts(start, products) }
    }

    override fun updateRecentProducts(products: List<ProductsItemType>) {
        updateProductsAdapterItem { updateRecentProducts(products) }
    }

    override fun setUpCarts(count: Int) {
        binding.ivCartCount.apply {
            if (count == 0) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                text = count.toString()
            }
        }
    }

    private fun updateProductsAdapterItem(action: ProductsAdapter.() -> Unit) {
        binding.productRecyclerview.adapter?.let {
            if (it is ProductsAdapter) {
                it.action()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PRODUCT_OFFSET, presenter.getOffset())
    }

    private fun navigateToCart() {
        startActivity(CartActivity.from(this))
    }
}
