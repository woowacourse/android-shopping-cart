package woowacourse.shopping.ui.cart.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun pagination(
    pageMoveToLeft: () -> Unit,
    pageMoveToLeftButtonEnabled: Boolean,
    currentPageIndex: Int,
    pageMoveToRight: () -> Unit,
    pageMoveToRightButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        pageMoveButton(
            onClick = pageMoveToLeft,
            shape =
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 0.dp,
                    bottomStart = 4.dp,
                    bottomEnd = 0.dp,
                ),
            enabled = pageMoveToLeftButtonEnabled,
        ) {
            Image(
                modifier = Modifier.size(width = 12.dp, height = 19.dp),
                painter = painterResource(R.drawable.previous_icon),
                contentDescription = stringResource(R.string.previous_page)
            )
        }
        Box(
            modifier = Modifier.size(42.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = (currentPageIndex + 1).toString(),
                fontWeight = FontWeight.W500,
                fontSize = 22.sp,
            )
        }
        pageMoveButton(
            onClick = pageMoveToRight,
            shape =
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 4.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 4.dp,
                ),
            enabled = pageMoveToRightButtonEnabled,
        ) {
            Image(
                painter = painterResource(R.drawable.next_icon),
                contentDescription = stringResource(R.string.next_page),
                modifier = Modifier.size(width = 12.dp, height = 19.dp),
            )
        }
    }
}

@Preview
@Composable
private fun paginationPreview() {
    pagination(
        pageMoveToLeft = {},
        pageMoveToLeftButtonEnabled = true,
        currentPageIndex = 1,
        pageMoveToRight = {},
        pageMoveToRightButtonEnabled = true,
    )
}
