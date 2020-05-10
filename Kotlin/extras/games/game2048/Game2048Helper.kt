package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {

    val listNotNull = filterNotNull().toMutableList()

    val mergeList = mutableListOf<T>()
    val index = 0
    var lastIndex = listNotNull.lastIndex
    var first: T
    var second: T? = null
    while (listNotNull.isNotEmpty()) {
        if (index == lastIndex) {
            mergeList.add(listNotNull[index])
            listNotNull.removeAt(index)
            continue
        }

        first = listNotNull[index]
        if (index + 1 <= lastIndex) {
            second = listNotNull[index + 1]
        }

        if (first == second) {
            mergeList.add(merge(first))
            listNotNull.removeAt(index + 1)
            listNotNull.removeAt(index)
        } else {
            mergeList.add(first)
            listNotNull.removeAt(index)
        }
        lastIndex = listNotNull.lastIndex
    }
    return mergeList

}
