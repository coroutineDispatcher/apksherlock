package data

import patterns

data class Line(
    val value: String,
    val shouldHighlight: Boolean = false,
)

fun String.toLine(): Line {
    val shouldHighlight = patterns.any { it.containsMatchIn(this) }
    return Line(this, shouldHighlight)
}
