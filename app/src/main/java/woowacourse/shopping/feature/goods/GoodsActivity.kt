package woowacourse.shopping.feature.goods

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityGoodsBinding
import woowacourse.shopping.domain.model.Goods
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.goods.adapter.GoodsAdapter
import woowacourse.shopping.feature.goods.adapter.GoodsClickListener
import woowacourse.shopping.feature.goods.adapter.MoreButtonAdapter
import woowacourse.shopping.feature.goodsdetails.GoodsDetailsActivity
import woowacourse.shopping.util.toUi

class GoodsActivity :
    AppCompatActivity(),
    GoodsClickListener {
    private lateinit var binding: ActivityGoodsBinding
    private val viewModel: GoodsViewModel by viewModels()
    private val goodsAdapter by lazy { GoodsAdapter(this) }
    private val moreButtonAdapter by lazy { MoreButtonAdapter { viewModel.addPage() } }
    private val concatAdapter by lazy { ConcatAdapter(goodsAdapter, moreButtonAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this

        binding.rvGoods.adapter = concatAdapter
        binding.viewModel = viewModel

        binding.rvGoods.layoutManager = getLayoutManager()
    }

    private fun getLayoutManager(): GridLayoutManager {
        val layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val (adapter, _) = concatAdapter.getWrappedAdapterAndPosition(position)
                    return when (adapter) {
                        is GoodsAdapter -> 1
                        else -> 2
                    }
                }
            }
        return layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_cart -> {
                val intent = CartActivity.newIntent(this)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigate(goods: Goods) {
        val intent = GoodsDetailsActivity.newIntent(this, goods.toUi())
        startActivity(intent)
    }

    override fun onClickGoods(goods: Goods) {
        navigate(goods)
    }
}
