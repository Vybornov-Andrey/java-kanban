package service;

import model.Task;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> mapNode = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void add(Task task) {
        if (task == null) return;
        remove(task.getId());
        Node<Task> newNode = linkLast(task);
        mapNode.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        Node<Task> nodeToRemove = mapNode.get(id);
        if (nodeToRemove != null) {
            removeNode(nodeToRemove);
            mapNode.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyList = new ArrayList<>();
        Node<Task> current = head;

        while (current != null) {
            historyList.add(current.item);
            current = current.next;
        }
        return historyList;
    }

    public List<Task> getTasks() {
        List<Task> historyList = new ArrayList<>();
        Node<Task> current = head;

        while (current != tail) {
            historyList.add(current.item);
            current = current.next;
        }
        if (head != null && current.item != null) {
            historyList.add(current.item);
            return historyList;
        } else return null;
    }

    public Node<Task> linkLast(Task task) {
        final Node<Task> newNode = new Node<>(tail, task, null);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        mapNode.put(task.getId(), newNode);
        return newNode;
    }

    public void removeNode(Node<Task> node) {
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;

        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }

        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }

        node.next = null;
        node.prev = null;
        node.item = null;
    }
}

    class Node<E> {
        public E item;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;

        }
    }