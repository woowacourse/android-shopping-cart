package woowacourse.shopping

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.presentation.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun initStartView() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
