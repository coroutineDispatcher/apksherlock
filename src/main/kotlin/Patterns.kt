val patterns =
    listOf(
        Regex("\\bIntent\\s*\\(.*?\\)"),
        Regex("\\bstartActivity\\s*\\(.*?\\)"),
        Regex("\\bsendBroadcast\\s*\\(.*?\\)"),
        Regex("\\bsetAllowFileAccessFromFileURLs\\s*\\(.*?\\)"),
        Regex("\\bsetAllowFileAccessFromFileURLs\\s*\\(.*?\\)"),
        Regex("\\bsetAllowUniversalAccessFromFileURLs\\s*\\(.*?\\)"),
        Regex(".*flag.*"),
        Regex("\\bSystem\\.loadLibrary\\s*\\(\\s*\".*?\"\\s*\\)")
    )
