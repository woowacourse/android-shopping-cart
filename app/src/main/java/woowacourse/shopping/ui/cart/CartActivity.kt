package woowacourse.shopping.ui.cart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartDatabase
import woowacourse.shopping.database.product.ProductDatabase
import woowacourse.shopping.databinding.ActivityCartBinding
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.cartAdapter.CartAdapter
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType
import woowacourse.shopping.ui.cart.cartAdapter.CartListener
import woowacourse.shopping.ui.detailedProduct.DetailedProductActivity

class CartActivity : AppCompatActivity(), CartContract.View {
    private lateinit var binding: ActivityCartBinding
    private lateinit var presenter: CartContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initToolbar()
        initPresenter(savedInstanceState)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.price = 345600
        binding.totalCount = 7
    }

    private fun initPresenter(savedInstanceState: Bundle?) {
        presenter = CartPresenter(
            this,
            CartDatabase(this),
            ProductDatabase(this),
            savedInstanceState?.getInt(KEY_OFFSET) ?: 0
        )
        presenter.setUpCarts()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setCarts(products: List<CartItemType.Cart>, pageUIModel: PageUIModel) {
        val cartListener = object : CartListener {
            override fun onPageNext() { presenter.moveToPageNext() }
            override fun onPagePrev() { presenter.moveToPagePrev() }
            override fun onItemRemove(productId: Int) { presenter.removeItem(productId) }
            override fun onItemClick(product: CartProductUIModel) {
                presenter.navigateToItemDetail(product.id)
            }
        }

        binding.rvProducts.adapter = CartAdapter(
            products.map { it }.plus(CartItemType.Navigation(pageUIModel)),
            cartListener
        )
    }

    override fun navigateToItemDetail(product: ProductUIModel) {
        startActivity(DetailedProductActivity.getIntent(this, product))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return false
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.getPageIndex().let {
            outState.putInt(KEY_OFFSET, it)
        }
    }
    companion object {
        private const val KEY_OFFSET = "KEY_OFFSET"
        fun getIntent(context: Context): Intent {
            return Intent(context, CartActivity::class.java)
        }
    }
}
