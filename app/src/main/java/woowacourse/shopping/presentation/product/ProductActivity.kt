package woowacourse.shopping.presentation.product

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ActivityProductBinding
import woowacourse.shopping.presentation.base.BindingActivity

class ProductActivity :
    BindingActivity<ActivityProductBinding>(R.layout.activity_product) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<ProductListFragment>(
                    R.id.fragment_container_shopping,
                    ProductListFragment.TAG,
                )
            }
        }
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = "꼬상"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_shopping, ProductDetailFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu, menu)
        return true
    }
}
