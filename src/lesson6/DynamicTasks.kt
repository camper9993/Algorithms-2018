@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File
import java.util.*

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    TODO()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Средняя
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
// Трудоемкость : N(n*log(n)) (алгоритм взят с wiki)
// Ресурсоемкость : R(n)
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val result: MutableList<Int> = mutableListOf()
    if (list.isEmpty()) return result
    val size = list.size
    val arrayLength: Array<Int?> = Array(size) { -1 }
    val arrayPath: Array<Int?> = Array(size) { -1 }
    var max: Int?
    var jMax: Int?
    for (i in 0 until size) {
        max = 0
        jMax = 0
        for (j in 0 until i) {
            if (list[j] < list[i] && arrayLength[j]!! > max!!) {
                max = arrayLength[j]
                jMax = j
            }
        }
        arrayLength[i] = max!! + 1
        arrayPath[i] = jMax
    }
    var n = 0
    for (k in 0 until size) {
        if (arrayLength[k]!! > arrayLength[n]!!) n = k
    }
    while (true) {
        if (n == 0) {
            result.add(list[n])
            break
        }
        result.add(list[n])
        n = arrayPath[n]!!
    }

    result.reverse()
    return result
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Сложная
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
//Трудоемскость : N(height * width)
//Ресурсоемкость : R(height * width)
fun shortestPathOnField(inputName: String): Int {
    val input = Scanner(File(inputName))
    val table: MutableList<Array<Int>> = mutableListOf()
    while (input.hasNextLine())
        table.add(input.nextLine().split(" ").map { it.toInt() }.toTypedArray())
    input.close()

    val width = table[0].size
    val height = table.size

    for (i in 0 until height)
        for (j in 0 until width) {
            table[i][j] = getBestMove(table, i, j) + table[i][j]
        }

    return table[height - 1][width - 1]
}

fun getBestMove(table: MutableList<Array<Int>>, i: Int, j: Int): Int {
    return if (i == 0 && j == 0) 0
    else if (i == 0) table[i][j - 1]
    else if (j == 0) table[i - 1][j]
    else Math.min(Math.min(table[i - 1][j - 1], table[i][j - 1]), table[i - 1][j])
}
// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5