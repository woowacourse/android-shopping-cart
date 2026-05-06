package woowacourse.shopping.presentation.cart.component

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.shopping.R

@Composable
fun Pagination(
    onPreviousPageClick: () -> Unit,
    hasPreviousPage: Boolean,
    currentPage: Int,
    onNextPageClick: () -> Unit,
    hasNextPage: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PageMoveButton(
            onClick = onPreviousPageClick,
            shape =
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 0.dp,
                    bottomStart = 4.dp,
                    bottomEnd = 0.dp,
                ),
            enabled = hasPreviousPage,
        ) {
            Image(
                modifier = Modifier.size(width = 12.dp, height = 19.dp),
                painter = painterResource(R.drawable.previous_icon),
                contentDescription = "previousIcon",
            )
        }
        Box(
            modifier = Modifier.size(42.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = currentPage.toString(),
                fontWeight = FontWeight.W500,
                fontSize = 22.sp,
            )
        }
        PageMoveButton(
            onClick = onNextPageClick,
            shape =
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 4.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 4.dp,
                ),
            enabled = hasNextPage,
        ) {
            Image(
                painter = painterResource(R.drawable.next_icon),
                contentDescription = "nextIcon",
                modifier = Modifier.size(width = 12.dp, height = 19.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PaginationPreview() {
    Pagination(
        onPreviousPageClick = {},
        hasPreviousPage = true,
        currentPage = 1,
        onNextPageClick = {},
        hasNextPage = true,
    )
}
