package lesson3;

import kotlin.NotImplementedError;
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
        root = delete(root, o);
        size--;
        return true;
    }

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
            else if (root.left != null)
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
        @Override
        public void remove() {
            if (stack.size() > 1) {
                delete(root,current.value);
                current = stack.pop();
            }
            else delete(root,current.value);
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
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> result = new TreeSet<>();
        int size = size();
        BinaryTreeIterator iterator = new BinaryTreeIterator();
        if (size == size()) {
            while (iterator.hasNext()) {
                T value = iterator.next();
                if (value.compareTo(toElement) < 0) {
                    result.add(value);
                }
            }
        }
            else {
                iterator = new BinaryTreeIterator();
                while (iterator.hasNext()) {
                    T value = iterator.next();
                    if (value.compareTo(toElement) < 0) {
                        result.add(value);
                    }
                }
            }
        return result;
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
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
