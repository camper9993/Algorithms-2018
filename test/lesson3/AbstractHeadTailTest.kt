package lesson3

import java.util.*
import kotlin.test.*

abstract class AbstractHeadTailTest {
    private lateinit var tree: SortedSet<Int>
    private lateinit var bigTree: SortedSet<Int>

    protected fun fillTree(empty: SortedSet<Int>) {
        this.tree = empty
        //В произвольном порядке добавим числа от 1 до 10
        tree.add(5)
        tree.add(1)
        tree.add(2)
        tree.add(7)
        tree.add(9)
        tree.add(10)
        tree.add(8)
        tree.add(4)
        tree.add(3)
        tree.add(6)
    }

    private fun fillBigTree(empty: SortedSet<Int>): SortedSet<Int> {
        val sortedSet = sortedSetOf<Int>()
        val random = Random()
        this.bigTree = empty
        for (i in 1..10000) {
            val number = random.nextInt(100000)
            bigTree.add(number)
            sortedSet.add(number)
        }
        return sortedSet
    }


    protected fun doHeadSetTest() {
        var set: SortedSet<Int> = tree.headSet(5)
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(false, set.contains(5))
        assertEquals(false, set.contains(6))
        assertEquals(false, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))


        set = tree.headSet(127)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

    }

    protected fun doTailSetTest() {
        var set: SortedSet<Int> = tree.tailSet(5)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(false, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))

        set = tree.tailSet(-128)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

    }

    protected fun doHeadSetRelationTest() {
        val set: SortedSet<Int> = tree.headSet(7)
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
        tree.add(0)
        assertTrue(set.contains(0))
        set.remove(4)
        assertFalse(tree.contains(4))
        tree.remove(6)
        assertFalse(set.contains(6))
        tree.add(12)
        assertFalse(set.contains(12))
        assertEquals(5, set.size)
        assertEquals(10, tree.size)
    }

    protected fun doTailSetRelationTest() {
        val set: SortedSet<Int> = tree.tailSet(4)
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        assertTrue(set.contains(12))
        set.remove(4)
        assertFalse(tree.contains(4))
        tree.remove(6)
        assertFalse(set.contains(6))
        tree.add(0)
        assertFalse(set.contains(0))
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
    }

    protected fun doSubSetTest() {
        val set: SortedSet<Int> = tree.subSet(4, 9)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))
    }

    protected fun doSubSetRelationTest() {
        val set: SortedSet<Int> = tree.subSet(4, 15)
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(14)
        assertTrue(set.contains(14))
        set.remove(6)
        assertFalse(tree.contains(6))
        tree.remove(14)
        assertFalse(set.contains(14))
        tree.add(0)
        assertFalse(set.contains(0))
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
    }

    protected fun doBigTest(headSet: Boolean, tailSet: Boolean) {
        val random = Random()
        if (headSet) {
            val checkSet: SortedSet<Int> = fillBigTree(BinaryTree<Int>())
            val set: SortedSet<Int> = bigTree
            val toElement: Int = set.elementAt(random.nextInt(set.size))
            assertEquals<SortedSet<*>>(set.headSet(toElement), checkSet.headSet(toElement))
        }
        if (tailSet) {
            val checkSet: SortedSet<Int> = fillBigTree(BinaryTree<Int>())
            val set: SortedSet<Int> = bigTree
            val fromElement: Int = set.elementAt(random.nextInt(set.size))
            assertEquals<SortedSet<*>>(set.tailSet(fromElement), checkSet.tailSet(fromElement))
        }
        if (!headSet && !tailSet) {
            val checkSet: SortedSet<Int> = fillBigTree(BinaryTree<Int>())
            val set: SortedSet<Int> = bigTree
            val firstBorder: Int = set.elementAt(random.nextInt(set.size))
            val secondBorder: Int = set.elementAt(random.nextInt(set.size))
            if (set.indexOf(firstBorder) < set.indexOf(secondBorder))
                assertEquals<SortedSet<*>>(set.subSet(firstBorder, secondBorder),
                        checkSet.subSet(firstBorder, secondBorder))
            else
                assertEquals<SortedSet<*>>(set.subSet(secondBorder, firstBorder),
                        checkSet.subSet(secondBorder,
                                firstBorder))
        }
    }

}