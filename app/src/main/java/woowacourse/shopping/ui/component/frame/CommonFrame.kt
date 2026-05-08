package woowacourse.shopping.ui.component.frame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.shopping.R

@Composable
fun CommonFrame(
    headerContent: @Composable () -> Unit,
    bodyContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Header { headerContent() }
        Body { bodyContent() }
    }
}

@Preview
@Composable
private fun CommonFramePreview() {
    CommonFrame(
        headerContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Shopping")
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "shopping icon",
                )
            }
        },
        bodyContent = {
            Text("Body")
        },
        modifier = Modifier,
    )
}
