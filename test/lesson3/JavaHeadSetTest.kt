package lesson3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import kotlin.test.Test

class JavaHeadSetTest : AbstractHeadTailTest() {

    @BeforeEach
    fun fillTree() {
        fillTree(BinaryTree<Int>())
    }

    @Test
    @Tag("Normal")
    fun headSetTest() {
        doHeadSetTest()
        doBigTest(true, false)
    }

    @Test
    @Tag("Hard")
    fun headSetRelationTest() {
        doHeadSetRelationTest()
    }

    @Test
    @Tag("Normal")
    fun tailSetTest() {
        doTailSetTest()
        doBigTest(false, true)
    }

    @Test
    @Tag("Hard")
    fun tailSetRelationTest() {
        doTailSetRelationTest()
    }

    @Test
    @Tag("Impossible")
    fun subSetTest() {
        doSubSetTest()
        doBigTest(false, false)
    }

    @Test
    fun subSetRelationTest() {
        doSubSetRelationTest()
    }
}