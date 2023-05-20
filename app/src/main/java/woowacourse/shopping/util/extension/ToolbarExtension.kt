package woowacourse.shopping.util.extension

import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar

fun Toolbar.findItem(@IdRes id: Int): MenuItem = menu.findItem(id)

fun Toolbar.getItemActionView(@IdRes id: Int): View? = findItem(id).actionView
