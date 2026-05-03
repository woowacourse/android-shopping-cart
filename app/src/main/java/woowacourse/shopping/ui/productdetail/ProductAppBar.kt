package woowacourse.shopping.ui.productdetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAppBar(onCloseClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(
                onClick = onCloseClick,
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = stringResource(R.string.close_description),
                    modifier = Modifier.padding(end = 10.dp),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF555555),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        ),
        modifier = modifier,
    )
}
