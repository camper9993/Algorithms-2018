package lesson3;

import kotlin.NotImplementedError;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        root = delete(root,o);
        if (root == null)
            return false;
        else {
            root = delete(root, o);
            size--;
            return true;
        }
    }
    //Ресурсоемкость = R(1)
    //Трудоемкость = О(h) h - высота дерева
    public Node<T> delete(Node<T> root, Object o) {
        @SuppressWarnings("unchecked")
        T key = (T) o;
        if (root == null) {
            return null;
        }
        int comparator = key.compareTo(root.value);
        if (comparator < 0)
            root.left = delete(root.left, o);
        else if (comparator > 0)
            root.right = delete(root.right, o);
        else {
            /** Если у root два дочерних узла, мы находим минимальный узел в правом поддереве root
             *  копируем его значение в root а потом удаляем минимальный узел в правом поддереве root.
             */
            if (root.left != null && root.right != null) {
                root.value = minNodeRightSubtree(root.right);
                root.right = delete(root.right, root.value);
            }
            else if (root.left != null) //Если у root один ребенок, то root = root.left || root.right
                return root.left;
            else
                return root.right;

        }
        return root;
    }

    private T minNodeRightSubtree(Node<T> node) {
        T min = node.value;
        while (node.left != null) {
            min = node.left.value;
            node = node.left;
        }
        return min;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {
        private Node<T> current;

        private Stack<Node<T>> stack = new Stack<>();

        //Ресурсоемкость = R(n) (в худшем случае, n - количество узлов)
        //Трудоемкость = О(n) n - кол - во узлов
        private BinaryTreeIterator() {
            current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public T next() {
            current = stack.pop();
            Node<T> stackUpdate = current;
            if (stackUpdate.right != null) {
                stackUpdate = stackUpdate.right;
                while (stackUpdate != null) {
                    stack.push(stackUpdate);
                    stackUpdate = stackUpdate.left;
                }
            }
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        //Ресурсоемкость = R(1)
        //Трудоемкость = O(h) h - высота дерева
        @Override
        public void remove() {
            root = delete(root,current.value);
            size--;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    //Трудоемкость : O(n)
    //Ресурсоемкость : R(1)
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new BinarySortedSet<>(this, toElement, fromElement, false, false);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    //Трудоемкость : O(n)
    //Ресурсоемкость : R(1)
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
       return new BinarySortedSet<>(this, toElement, null, true, false);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    //Трудоемкость : O(n)
    //Ресурсоемкость : R(1)
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new BinarySortedSet<>(this, null, fromElement, false, true);

    }

    public class BinarySortedSet<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {
        T upBoarder;
        T lowBoarder;
        boolean toLast;
        boolean fromFirst;
        BinaryTree tree;

        BinarySortedSet(BinaryTree tree,T upBoarder, T lowBoarder, boolean fromFirst, boolean toLast) {
            this.upBoarder = upBoarder;
            this.lowBoarder = lowBoarder;
            this.fromFirst = fromFirst;
            this.toLast = toLast;
            this.tree = tree;
        }

        public boolean inRange(Object o) {
            T t = (T) o;
            if (lowBoarder != null && upBoarder != null) {
                return t.compareTo(lowBoarder) >= 0 && t.compareTo(upBoarder) < 0;
            } else if (lowBoarder == null) {
                return t.compareTo(upBoarder) < 0;
            } else return t.compareTo(lowBoarder) >= 0;
        }

        @Override
        public boolean add(T t) {
            if (inRange(t)) {
                tree.add(t);
                return true;
            }
            else return false;
        }
        @Override
        public boolean remove(Object o) {
            if (inRange(o)) {
                tree.remove(o);
                return true;
            }
            else return false;
        }
        @Override
        public boolean contains(Object o) {
            if (inRange(o)) {
                return tree.contains(o);
            }
            return false;
        }

        @Override
        public Iterator<T> iterator() {
            return null;
        }

        @Override
        public int size() {
            int size = 0;
            for (T aTree : (Iterable<T>) tree) {
                if (inRange(aTree))
                    size++;
            }
            return size;
        }

        @Nullable
        @Override
        public Comparator<? super T> comparator() {
            return null;
        }

        @NotNull
        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            return null;
        }

        @NotNull
        @Override
        public SortedSet<T> headSet(T toElement) {
            return null;
        }

        @NotNull
        @Override
        public SortedSet<T> tailSet(T fromElement) {
            return null;
        }

        @Override
        public T first() {
            return null;
        }

        @Override
        public T last() {
            return null;
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
