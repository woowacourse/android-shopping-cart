package woowacourse.shopping.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.model.data.AlsongDatabase
import woowacourse.shopping.model.data.ProductsImpl

class ProductDetailActivity : AppCompatActivity(), CartButtonClickListener {
    private lateinit var binding: ActivityProductDetailBinding
    private var toast: Toast? = null
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ProductDetailViewModelFactory(
                ProductsImpl,
                AlsongDatabase.getDatabase(applicationContext).orderDao(),
                AlsongDatabase.getDatabase(applicationContext).recentProductDao(),
            ),
        )
            .get(ProductDetailViewModel::class.java)
    }
    private val productId by lazy { productId() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        showProductDetail()
        setOnCartButtonClickListener()
    }

    private fun setOnCartButtonClickListener() {
        binding.cartButtonClickListener = this
    }

    private fun showProductDetail() {
        runCatching {
            viewModel.loadProduct(productId)
            viewModel.loadRecentProduct()
        }.onSuccess {
            viewModel.product.observe(this) {
                binding.product = it
                binding.lifecycleOwner = this
            }
        }.onFailure {
            toast = Toast.makeText(this, it.message, Toast.LENGTH_SHORT)
            toast?.show()
            finish()
        }
    }

    override fun onClick() {
        viewModel.addProductToCart()
        toast?.cancel()
        toast = Toast.makeText(this, getString(R.string.add_cart_complete), Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_close -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun productId() =
        intent.getLongExtra(
            ProductDetailKey.EXTRA_PRODUCT_KEY,
            EXTRA_DEFAULT_VALUE,
        )

    companion object {
        private const val EXTRA_DEFAULT_VALUE = -1L

        fun startActivity(
            context: Context,
            productId: Long,
        ) = Intent(context, ProductDetailActivity::class.java).run {
            putExtra(ProductDetailKey.EXTRA_PRODUCT_KEY, productId)
            context.startActivity(this)
        }
    }
}
