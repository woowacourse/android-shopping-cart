package woowacourse.shopping.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import woowacourse.shopping.R
import woowacourse.shopping.data.ShoppingItemsRepositoryImpl
import woowacourse.shopping.databinding.ActivityDetailBinding

class DetailActivity : ComponentActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val productId: Long by lazy { intent.getLongExtra(PRODUCT_ID, INVALID_PRODUCT_ID) }
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        val factory = DetailViewModelFactory(ShoppingItemsRepositoryImpl(), productId = productId)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        binding.toolbarDetail.setOnMenuItemClickListener {
            finish()
            true
        }

        binding.vmProduct = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuItemSelected(
        featureId: Int,
        item: MenuItem,
    ): Boolean {
        when (item.itemId) {
            R.id.back_action -> {
                finish()
                return true
            }
        }
        return super.onMenuItemSelected(featureId, item)
    }

    companion object {
        const val PRODUCT_ID = "product_id"
        const val INVALID_PRODUCT_ID = -1L

        fun createIntent(
            context: Context,
            productId: Long,
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
        }
    }
}
