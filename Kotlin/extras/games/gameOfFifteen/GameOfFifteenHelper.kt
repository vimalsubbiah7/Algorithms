package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val list = permutation.toMutableList()
    var count = 0
    for (i in 0..list.lastIndex) {
        for (j in 0..list.lastIndex) {
            if (i < j && list[i] > list[j]) {
                list[i] = list[j].also { list[j] = list[i] }
                count++
            }
        }
    }

    return count.rem(2) ==0
}