package woowacourse.shopping.presentation.shopping.product

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
    }
}
