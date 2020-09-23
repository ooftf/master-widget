fun String.isDouble(): Boolean {
    val toDoubleOrNull = toDoubleOrNull()
    return toDoubleOrNull != null
}