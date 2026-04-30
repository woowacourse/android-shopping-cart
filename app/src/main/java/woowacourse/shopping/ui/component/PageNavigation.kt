package woowacourse.shopping.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import woowacourse.shopping.R
import woowacourse.shopping.ui.theme.AndroidShoppingTheme

@Composable
fun PageNavigation(
    currentPage: Int,
    canMoveToPreviousPage: Boolean,
    canMoveToNextPage: Boolean,
    onBeforePageClick: () -> Unit,
    onNextPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerButtonColors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Gray,
        )

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(56.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            enabled = canMoveToPreviousPage,
            onClick = onBeforePageClick,
            modifier = Modifier.size(42.dp),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                bottomStart = 4.dp,
            ),
            colors = pagerButtonColors,
            contentPadding =  PaddingValues(0.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.left_icon),
                contentDescription = stringResource(R.string.previous_page_description),
                tint = Color.White,
                modifier = Modifier.size(16.dp),
            )
        }

        Text(
            text = (currentPage + 1).toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 12.dp),
        )

        Button(
            enabled = canMoveToNextPage,
            onClick = onNextPageClick,
            modifier = Modifier.size(42.dp),
            shape = RoundedCornerShape(
                topEnd = 4.dp,
                bottomEnd = 4.dp,
            ),
            colors = pagerButtonColors,
            contentPadding =  PaddingValues(0.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.right_icon),
                contentDescription = stringResource(R.string.next_page_description),
                tint = Color.White,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PageNavigationPreview(
    @PreviewParameter(PageNavigationPreviewProvider::class)
    state: PageNavigationPreviewState,
) {
    AndroidShoppingTheme {
        PageNavigation(
            currentPage = state.currentPage,
            canMoveToPreviousPage = state.canMoveToPreviousPage,
            canMoveToNextPage = state.canMoveToNextPage,
            onBeforePageClick = {},
            onNextPageClick = {},
            modifier = Modifier,
        )
    }
}

private data class PageNavigationPreviewState(
    val currentPage: Int,
    val canMoveToPreviousPage: Boolean,
    val canMoveToNextPage: Boolean,
)

private class PageNavigationPreviewProvider : PreviewParameterProvider<PageNavigationPreviewState> {
    override val values =
        sequenceOf(
            PageNavigationPreviewState(
                currentPage = 1,
                canMoveToPreviousPage = false,
                canMoveToNextPage = true,
            ),
            PageNavigationPreviewState(
                currentPage = 2,
                canMoveToPreviousPage = true,
                canMoveToNextPage = true,
            ),
            PageNavigationPreviewState(
                currentPage = 5,
                canMoveToPreviousPage = true,
                canMoveToNextPage = false,
            ),
        )
}
