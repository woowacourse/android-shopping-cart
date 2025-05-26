package woowacourse.shopping.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.ShoppingApplication
import woowacourse.shopping.databinding.ActivityDetailBinding
import woowacourse.shopping.product.catalog.ProductUiModel
import woowacourse.shopping.product.catalog.QuantityControlListener
import woowacourse.shopping.util.intentParcelableExtra

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        applyWindowInsets()
        setSupportActionBar()
        setAddToCartClickListener()

        val product: ProductUiModel? = productFromIntent()
        product?.let {
            binding.product = it
            binding.layoutQuantityControlBar.product = it
            setViewModel(it)
            observeProduct()
        }
        binding.vm = viewModel
        binding.lifecycleOwner = this
        binding.layoutQuantityControlBar.quantityControlListener =
            QuantityControlListener { event, _ ->
                if (event == 1) {
                    viewModel.increaseQuantity()
                } else {
                    viewModel.decreaseQuantity()
                }
            }
        binding.layoutLatestViewedProduct.latestViewedProductClickListener =
            LatestViewedProductClickListener { product ->
                val intent = DetailActivity.newIntent(this, product)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        viewModel.setLatestViewedProduct()
    }

    private fun setViewModel(product: ProductUiModel) {
        viewModel =
            ViewModelProvider(
                this,
                DetailViewModelFactory(product, application as ShoppingApplication),
            )[DetailViewModel::class.java]
    }

    private fun observeProduct() {
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.product.observe(this) {
            viewModel.setQuantity()
            viewModel.setPriceSum()
            viewModel.addToRecentlyViewedProduct()
        }

        viewModel.quantity.observe(this) {
            viewModel.setPriceSum()
        }

        viewModel.latestViewedProduct.observe(this) { product ->
            binding.layoutLatestViewedProduct.product = product
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_back_menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_detail_back -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun setAddToCartClickListener() {
        binding.addToCartClickListener =
            AddToCartClickListener { product ->
                showToastMessage()
                viewModel.addToCart()
                finish()
            }
    }

    private fun setSupportActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.title = ""
    }

    private fun showToastMessage() {
        Toast
            .makeText(this, getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT)
            .show()
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun productFromIntent(): ProductUiModel? = intent.intentParcelableExtra(KEY_PRODUCT_DETAIL, ProductUiModel::class.java)

    companion object {
        private const val KEY_PRODUCT_DETAIL = "productDetail"

        fun newIntent(
            context: Context,
            product: ProductUiModel,
        ): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_PRODUCT_DETAIL, product)
            }
    }
}
