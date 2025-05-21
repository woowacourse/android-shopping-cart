package woowacourse.shopping.feature.goods

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.ScrollListener
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.cart.ViewModelFactory
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter
import woowacourse.shopping.feature.goods.adapter.GoodsViewHolder
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity
import woowacourse.shopping.util.toUi

class GoodsActivity :
    AppCompatActivity(),
    GoodsViewHolder.GoodsClickListener {
    private lateinit var binding: ActivityGoodsBinding
    private val adapter: GoodsAdapter by lazy { GoodsAdapter(this) }
    private val viewModel: GoodsViewModel by viewModels {
        ViewModelFactory { GoodsViewModel(CartRepositoryImpl(CartDatabase.getDatabase(this))) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        binding.rvGoods.adapter = adapter
        binding.viewModel = viewModel

        updateMoreButton()
        viewModel.isSuccess.observe(this) {
            Toast.makeText(this, R.string.goods_detail_cart_insert_success_toast_message, Toast.LENGTH_SHORT).show()
        }
        viewModel.isFail.observe(this) {
            Toast.makeText(this, R.string.goods_detail_cart_insert_fail_toast_message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateMoreButton() {
        viewModel.isFullLoaded.observe(this) { isFullLoaded ->
            binding.rvGoods.clearOnScrollListeners()
            binding.rvGoods.addOnScrollListener(
                ScrollListener(
                    shouldShowButton = { !isFullLoaded },
                    onVisibilityChange = viewModel::updateMoreButtonVisibility,
                ),
            )
        }
    }

    private fun navigate(goods: Goods) {
        val intent = GoodsDetailsActivity.newIntent(this, goods.toUi())
        startActivity(intent)
    }

    override fun onClickGoods(goods: Goods) {
        navigate(goods)
    }

    override fun insertToCart(goods: Goods) {
        viewModel.insertToCart(goods)
    }
}
