package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.topAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cartTopAppBar(onClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(R.drawable.arrow_left_alt_24px),
                contentDescription = stringResource(R.string.left_arrow_icon),
                modifier =
                    Modifier
                        .size(40.dp)
                        .clickable { onClick() },
                colorFilter = ColorFilter.tint(Color.White),
            )
        },
        title = {
            Text(
                text = stringResource(R.string.cart),
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
                color = Color.White,
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = topAppBarColor,
                titleContentColor = Color.White,
            ),
    )
}

@Preview
@Composable
private fun cartTopAppBarPreview() {
    cartTopAppBar(onClick = {})
}
