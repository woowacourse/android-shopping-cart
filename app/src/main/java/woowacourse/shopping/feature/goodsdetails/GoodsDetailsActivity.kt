package woowacourse.shopping.feature.goodsdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityGoodsDetailsBinding
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.feature.cart.ViewModelFactory
import kotlin.getValue

class GoodsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsDetailsBinding
    private lateinit var goods: GoodsUiModel
    private val viewModel: GoodsDetailsViewModel by viewModels {
        ViewModelFactory { GoodsDetailsViewModel(CartRepositoryImpl(CartDatabase.getDatabase(this))) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goods = IntentCompat.getParcelableExtra(intent, GOODS_KEY, GoodsUiModel::class.java) ?: return
        binding.goods = goods
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isSuccess.observe(this) { isSuccess ->
            val message: String =
                getString(
                    if (isSuccess) {
                        R.string.goods_detail_cart_insert_success_toast_message
                    } else {
                        R.string.goods_detail_cart_insert_fail_toast_message
                    },
                )

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_close, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_close -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val GOODS_KEY = "GOODS"

        fun newIntent(
            context: Context,
            goods: GoodsUiModel,
        ): Intent =
            Intent(context, GoodsDetailsActivity::class.java).apply {
                putExtra(GOODS_KEY, goods)
            }
    }
}
