package data

import patterns

data class Line(
    val value: String,
    val matchesPatterns: Boolean = false,
)

fun String.toLine(): Line {
    val matchesPatterns = patterns.any { it.containsMatchIn(this) }
    return Line(this, matchesPatterns)
}
