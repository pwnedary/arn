/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * An element with a {@link #parent} and {@link #children}s.
 * @author pwnedary List
 */
public class Node<E> implements Cloneable {

	private E element;
	private Node<E> parent = null;
	private final List<Node<E>> children = new ArrayList<Node<E>>();

	/**
	 * 
	 */
	public Node(E e) {
		this.element = e;
	}

	/**
	 * 
	 */
	public Node() {
		this(null);
	}

	public void add(Node<E> node) {
		node.parent = this;
		children.add(node);
	}

	/**
	 * @return This node's element and all of it's child's in a list.
	 */
	public List<E> asList() {
		List<E> list = new ArrayList<E>();
		list.add(element);
		for (int i = 0; i < children.size(); i++)
			list.addAll(children.get(i).asList());
		return list;
	}

	@Override
	public boolean equals(Object obj) {
		return element.equals(((Node<?>) obj).element) && (parent == null && ((Node<?>) obj).parent == null) || parent.equals(((Node<?>) obj).parent) && children.equals(((Node<?>) obj).children);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return element + (children.size() > 0 ? ", " + children : "");
	}

	/* Getters and Setters */

	/**
	 * @return the element
	 */
	public E getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(E element) {
		this.element = element;
	}

	/**
	 * @return the parent
	 */
	public Node<E> getParent() {
		return parent;
	}

	/**
	 * @return the children
	 */
	public List<Node<E>> getChildren() {
		return children;
	}

}