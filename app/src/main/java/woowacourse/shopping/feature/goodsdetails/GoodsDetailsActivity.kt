package woowacourse.shopping.feature.goodsdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityGoodsDetailsBinding
import woowacourse.shopping.feature.GoodsUiModel
import woowacourse.shopping.util.toDomain

class GoodsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoodsDetailsBinding
    private lateinit var goods: GoodsUiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        goods = IntentCompat.getParcelableExtra(intent, GOODS_KEY, GoodsUiModel::class.java) ?: return
        binding.goods = goods
        binding.insertCallback = { insert(goods) }
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

    private fun insert(goods: GoodsUiModel) {
        CartRepositoryImpl(CartDatabase.getDatabase(this)).insert(goods.toDomain()) {
            Toast
                .makeText(
                    this,
                    getString(R.string.goods_detail_cart_insert_complete_toast_message),
                    Toast.LENGTH_SHORT,
                ).show()
        }
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
