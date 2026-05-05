package woowacourse.shopping.ui.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import woowacourse.shopping.ui.component.topbar.TopBarType.LEADING
import woowacourse.shopping.ui.component.topbar.TopBarType.TAILING
import woowacourse.shopping.ui.component.topbar.TopBarType.TITLE

@Composable
fun ShoppingTopBar(
    type: TopBarType,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    val paddingValues = when (type) {
        LEADING -> PaddingValues(start = 26.dp, end = 20.dp, top = 16.dp, bottom = 16.dp)
        TITLE -> PaddingValues(horizontal = 23.dp, vertical = 20.dp)
        TAILING -> PaddingValues(end = 26.dp, top = 16.dp, bottom = 16.dp)
    }

    val arrangement = when (type) {
        LEADING -> Arrangement.SpaceBetween
        TITLE -> Arrangement.spacedBy(25.dp)
        TAILING -> Arrangement.End
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF555555))
            .padding(paddingValues),
        horizontalArrangement = arrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}
