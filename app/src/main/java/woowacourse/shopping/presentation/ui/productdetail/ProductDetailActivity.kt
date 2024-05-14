package woowacourse.shopping.presentation.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.repsoitory.DummyProductList
import woowacourse.shopping.data.repsoitory.DummyShoppingCart
import woowacourse.shopping.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            title = getString(R.string.product_detail_title)
        }
        viewModel =
            ViewModelProvider(
                this,
                ProductDetailViewModelFactory(DummyProductList, DummyShoppingCart),
            )[ProductDetailViewModel::class.java]

        binding =
            ActivityProductDetailBinding.inflate(layoutInflater).apply {
                vm = viewModel
                lifecycleOwner = this@ProductDetailActivity
            }
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_detail_menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_detai_closed -> finish()
        }
        return true
    }

    companion object {
        const val PUT_EXTRA_PRODUCT_ID = "product_id"

        fun startActivity(
            context: Context,
            id: Int,
        ) {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PUT_EXTRA_PRODUCT_ID, id)
            context.startActivity(intent)
        }
    }
}
