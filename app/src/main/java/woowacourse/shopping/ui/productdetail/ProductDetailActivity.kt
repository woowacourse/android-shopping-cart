package woowacourse.shopping.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.database.cart.CartRepositoryImpl
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.databinding.DialogAddCartBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private val binding: ActivityProductDetailBinding by lazy {
        ActivityProductDetailBinding.inflate(layoutInflater)
    }
    private val dialogBinding: DialogAddCartBinding by lazy {
        DialogAddCartBinding.inflate(layoutInflater)
    }
    private val presenter: ProductDetailContract.Presenter by lazy {
        ProductDetailPresenter(
            this,
            ProductRepositoryImpl,
            CartRepositoryImpl(this, ProductRepositoryImpl),
            RecentlyViewedProductRepositoryImpl(this, ProductRepositoryImpl),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionBar()
        presenter.init(intent.getLongExtra(PRODUCT_ID, -1))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun setProduct(product: ProductDetailUIState) {
        Glide.with(this)
            .load(product.imageUrl)
            .into(binding.ivProductDetail)

        binding.detailProduct = product
        binding.btnProductDetailAdd.setOnClickListener {
            makeDialog(product).show()
        }
    }

    private fun makeDialog(product: ProductDetailUIState): AlertDialog.Builder {
        dialogBinding.product = product
        setDialogCountButtonListener()

        return AlertDialog.Builder(this)
            .setTitle(product.name)
            .setView(dialogBinding.root)
            .setPositiveButton(getString(R.string.dialog_add_cart_positive)) { _, _ ->
                presenter.addProductToCart(
                    product.id,
                    dialogBinding.tvDialogCount.text.toString().toInt(),
                )
                presenter.navigateToCart()
            }
    }

    private fun setDialogCountButtonListener() {
        dialogBinding.btnDialogPlusCount.setOnClickListener {
            presenter.addDialogCount(dialogBinding.tvDialogCount.text.toString().toInt())
        }
        dialogBinding.btnDialogMinusCount.setOnClickListener {
            presenter.minusDialogCount(dialogBinding.tvDialogCount.text.toString().toInt())
        }
    }

    override fun showLastlyViewedProduct(product: RecentlyViewedProductUIState) {
        binding.recentProduct = product
        binding.onRecentProductClick = presenter::navigateToProductDetail
    }

    override fun hideLastlyViewedProduct() {
        binding.layoutLastlyViewed.isVisible = false
    }

    override fun updateCount(count: Int) {
        dialogBinding.tvDialogCount.text = count.toString()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, getString(R.string.error_not_found), Toast.LENGTH_SHORT).show()
    }

    override fun moveToProductDetailActivity(productId: Long) {
        startActivity(
            ProductDetailActivity.getIntent(this, productId).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }

    override fun moveToCartActivity() {
        finish()
        startActivity(
            CartActivity.getIntent(this),
        )
    }

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"

        fun getIntent(context: Context, productId: Long): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
        }
    }
}
