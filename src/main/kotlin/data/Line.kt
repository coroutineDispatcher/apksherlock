package data

data class Line(
    val value: String,
    val shouldHighlight: Boolean = false
)

fun String.toLine(): Line {
    val intentPattern = Regex("Intent\\s*\\(.*?\\)")
    val shouldOverrideUrlLoadingPattern = Regex("shouldOverrideUrlLoading\\s*\\(.*?\\)")
    val startActivityPattern = Regex("startActivity\\s*\\(.*?\\)")
    val sendBroadcastPattern = Regex("sendBroadcast\\s*\\(.*?\\)")

    /*
        If this grows it will be unreadable. Todo: Find a better solution
     */
    val shouldHighlight = intentPattern.containsMatchIn(this) || shouldOverrideUrlLoadingPattern.containsMatchIn(this)
        || startActivityPattern.containsMatchIn(this) || sendBroadcastPattern.containsMatchIn(this)

    return Line(
        this,
            shouldHighlight
    )
}
