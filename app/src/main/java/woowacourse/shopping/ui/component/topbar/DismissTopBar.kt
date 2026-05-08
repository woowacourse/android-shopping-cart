package woowacourse.shopping.ui.component.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R

@Composable
fun DismissTopBar(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ShoppingTopBar(
        paddingValues = PaddingValues(end = 26.dp, top = 16.dp, bottom = 16.dp),
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_dismiss),
            contentDescription = "닫기 버튼",
            modifier = Modifier
                .size(16.dp)
                .clickable(onClick = onDismiss),
            tint = Color.White,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DismissTopBarPreview() {
    DismissTopBar(
        onDismiss = { },
    )
}
