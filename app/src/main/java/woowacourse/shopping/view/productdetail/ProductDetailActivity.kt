package woowacourse.shopping.view.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.CartProductRepositoryImpl
import woowacourse.shopping.data.RecentViewedRepositoryImpl
import woowacourse.shopping.data.db.CartDBHelper
import woowacourse.shopping.data.server.ProductServiceImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.ProductModel
import woowacourse.shopping.util.getParcelableCompat
import woowacourse.shopping.view.cart.CartActivity

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var presenter: ProductDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setContentView(binding.root)
        setPresenter()
        setActionBar()
        getData()
        bindView()
        showDialog()
        setMostRecentViewedVisibility()
        presenter.updateLatestViewedProducts()
    }

    private fun setBinding() {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
    }

    private fun setPresenter() {
        presenter =
            ProductDetailPresenter(this, CartProductRepositoryImpl(CartDBHelper(this)), RecentViewedRepositoryImpl(this), ProductServiceImpl())
    }

    private fun setActionBar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
    }

    override fun getData() {
        intent.getParcelableCompat<ProductModel>(PRODUCT)?.let { presenter.setProductData(it) }
        intent.getBooleanExtra(FLAG, false).let { presenter.setFlag(it) }
    }

    private fun bindView() {
        binding.product = presenter.getProductData()
        binding.recentViewedProduct = presenter.getLatestViewedProductData()
        binding.presenter = presenter
    }

    private fun setMostRecentViewedVisibility() {
        presenter.compareNowAndRecent()
        if (presenter.getFlag()) {
            binding.lastProductLayout.visibility = View.INVISIBLE
        }
    }

    private fun showDialog() {
        binding.btnPutInCart.setOnClickListener {
            val dialog = CartDialog(
                this,
                CartProductRepositoryImpl(CartDBHelper(this)),
                presenter.getProductData(),
            )
            dialog.show()
        }
    }

    override fun startCartActivity() {
        val intent = CartActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    override fun startRecentViewedDetail(product: ProductModel) {
        val intent = newIntent(this, product, true)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.navigateNextStep(item.itemId)
        println("onOptionsItemSelected")
        return super.onOptionsItemSelected(item)
    }

    override fun handleBackButtonClicked() {
        finish()
    }

    companion object {
        const val PRODUCT = "PRODUCT"
        const val FLAG = "flag"

        fun newIntent(context: Context, product: ProductModel, flag: Boolean): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT, product)
            intent.putExtra(FLAG, flag)
            return intent
        }
    }
}
