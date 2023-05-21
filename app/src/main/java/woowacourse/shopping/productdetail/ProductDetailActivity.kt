package woowacourse.shopping.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.databinding.DialogCountViewBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.util.generateProductDetailPresenter
import woowacourse.shopping.util.getSerializableCompat

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {

    private lateinit var binding: ActivityProductDetailBinding

    private val product: ProductUiModel by lazy { intent.getSerializableCompat(PRODUCT_KEY)!! }
    private val isRecentProduct: Boolean by lazy {
        intent.getBooleanExtra(
            IS_RECENT_PRODUCT,
            false,
        )
    }
    private val presenter: ProductDetailPresenter by lazy {
        generateProductDetailPresenter(this, product, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        binding.presenter = presenter
        presenter.setUpView()
        setUpProductDetailToolbar()
    }

    private fun setUpProductDetailToolbar() {
        setSupportActionBar(binding.toolbarProductDetail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_close, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        setResult(DETAIL_ACTIVITY_RESULT_CODE)
    }

    override fun setUpRecentViewedProduct(product: ProductUiModel?) {
        if (isRecentProduct || product == null) {
            binding.recentViewedProduct.visibility = View.GONE
        } else {
            binding.textRecentProductName.text = product.name
            binding.textRecentProductPrice.text =
                getString(R.string.price_format, product.toPriceFormat())
            binding.recentViewedProduct.setOnClickListener {
                navigateToRecentProductView(product)
            }
        }
    }

    override fun showCountProductView() {
        val binding = DialogCountViewBinding.inflate(LayoutInflater.from(this))
        binding.presenter = presenter
        val dialog: AlertDialog = createCountDialog(binding)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun createCountDialog(binding: DialogCountViewBinding) =
        AlertDialog.Builder(this).apply {
            setView(binding.root)
            binding.countView.count = presenter.count
            binding.countView.plusClickListener = {
                presenter.changeCount(true)
            }
            binding.countView.minusClickListenerInCart = {
                presenter.changeCount(false)
            }
        }.create()

    override fun navigateToShoppingCartView() {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRecentProductView(product: ProductUiModel) {
        val intent = getRecentViewIntent(this, product)
        startActivity(intent)
        finish()
    }

    private fun getRecentViewIntent(context: Context, product: ProductUiModel): Intent {
        val intent = Intent(context, ProductDetailActivity::class.java).apply {
            putExtra(PRODUCT_KEY, product)
            putExtra(IS_RECENT_PRODUCT, true)
        }
        return intent
    }

    companion object {
        private const val PRODUCT_KEY = "product"
        private const val IS_RECENT_PRODUCT = "is_recent_product"
        const val DETAIL_ACTIVITY_RESULT_CODE = 0

        fun getIntent(context: Context, product: ProductUiModel): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
            }
            return intent
        }
    }
}
