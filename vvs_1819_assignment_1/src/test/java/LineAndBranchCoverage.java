import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class LineAndBranchCoverage {

	/// CLONE
	/**
	 * Caso de teste para o método clone usado o método equals
	 */
	@Test
	public void testCloneTree() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		ArrayNTree<Integer> tree2 = tree.clone();
		boolean equals = tree.equals(tree2);
		assertTrue(equals);
	}

	/// CONTAINS
	/*
	 * Na linha 155 do método contains é impossível cobrir um dos branches,
	 * nomeadamente o caso em que o compareTo == 0 || contains(elem) == false, uma
	 * vez que o compareTo == 0 indica que existe um elemento na árvore mas ao mesmo
	 * tempo contains(elem) == false indica que não existe.
	 */
	@Test
	public void testEmptyTreeContains() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);

		boolean contains = tree.contains(1);
		assertFalse(contains);
	}

	@Test
	public void testElementAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean contains = tree.contains(1);
		assertTrue(contains);
	}

	@Test
	public void testTreeDoesNotContainSmaller() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(5);
		assertFalse(contains);
	}

	@Test
	public void testEqualAndContains() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(85);
		assertTrue(contains);
	}

	@Test
	public void testDoesNotContainLarger() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(90);
		assertFalse(contains);
	}

	/// COUNT LEAVES
	/**
	 * Caso de teste para o método count leaves com uma árvore apenas com raiz
	 */
	@Test
	public void testTreeWithOneElementCountLeaves() {
		List<Integer> list = Arrays.asList(17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 1);

		int leaves = tree.countLeaves();
		assertEquals(1, leaves, "number of leaves");
	}

	/**
	 * Caso de teste para o método count leaves com árvore com vários elementos
	 */
	@Test
	public void testTreeMoreElementsCountLeaves() {
		List<Integer> list = Arrays.asList(39, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int leaves = tree.countLeaves();
		assertEquals(1, leaves, "number of leaves");
	}

	/// DELETE

	@Test
	public void testDeleteEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete empty");
	}

	@Test
	public void testDeleteRoot() {
		List<Integer> list = Arrays.asList(1, 2);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete root");
	}

	@Test
	public void testDeleteTreeWithTwoElements() {
		List<Integer> list = Arrays.asList(1, 2);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete root");
	}

	@Test
	public void testDeleteSmallerThanRoot() {
		List<Integer> list = Arrays.asList(2, 3, 4);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(1);
		int size = tree.size();
		assertEquals(3, size, "delete root");
	}

	@Test
	public void testBiggerThanTree() {
		List<Integer> list = Arrays.asList(1, 2, 3);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(4);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	@Test
	public void testSmallerThanChildren() {
		List<Integer> list = Arrays.asList(1, 3, 4);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(2);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	@Test
	public void testDeleteCompact() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 4);

		tree.delete(6);
		int size = tree.size();
		assertEquals(7, size, "insert");
	}

	/// EQUALS

	/**
	 * Caso de teste para o método equals a comparar duas árvores vazias
	 */
	@Test
	public void testeTwoEmptyTrees() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(1);
		assertThrows(NullPointerException.class, () -> {
			tree.equals(tree2);
		});
	}

	/**
	 * Caso de teste para o método equals a comparar a árvore consigo própria
	 */
	@Test
	public void testComparingItself() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(tree);
		assertTrue(equals);
	}

	/**
	 * Caso de teste para o método equals a comparar duas árvores iguais
	 */
	@Test
	public void testEqualTrees() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(tree2);
		assertTrue(equals);
	}

	/**
	 * Caso de teste para o método equals a comparar duas árvores, com a primeira
	 * superior à segunda
	 */
	@Test
	public void testFirstTreeBigger() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		List<Integer> list2 = Arrays.asList(10, 20);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list2, 2);

		boolean equals = tree.equals(tree2);
		assertFalse(equals);
	}

	/**
	 * Caso de teste para o método equals a comparar duas árvores, com a segunda
	 * superior à primeira
	 */
	@Test
	public void testSecondTreeBigger() {
		List<Integer> list1 = Arrays.asList(10, 20);
		List<Integer> list2 = Arrays.asList(10, 20, 22);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 2);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list2, 3);

		boolean equals = tree.equals(tree2);
		assertFalse(equals);
	}

	/**
	 * Caso de teste para o método equals a comparar duas árvores diferentes
	 */
	@Test
	public void testNotEqualTrees() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		List<Integer> list2 = Arrays.asList(10, 20, 22);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list2, 3);

		boolean equals = tree.equals(tree2);
		assertFalse(equals);
	}

	/**
	 * Caso de teste para o método equals a comparar uma árvore com um outro objeto
	 */
	@Test
	public void testObjectOther() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(0);
		assertFalse(equals);
	}

	/// HEIGHT

	/**
	 * Caso de teste para o método height para uma árvore vazia
	 */
	@Test
	public void testEmptyTreeHeight() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		int height = tree.height();
		assertEquals(0, height, "height of tree");
	}

	@Test
	public void testTreeWithMoreElementsHeight() {
		List<Integer> list = Arrays.asList(39, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int height = tree.height();
		assertEquals(2, height, "height of tree");
	}

	/// INFO
	@Test
	public void testEmptyTreeInfo() {
		List<Integer> list1 = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 6);
		String infoTree = tree.info();

		int size = tree.size();
		int height = tree.height();
		int countLeaves = tree.countLeaves();
		String info = tree + ", size: " + size + ", height: " + height + ", nLeaves: " + countLeaves;

		boolean equals = info.equals(infoTree);
		assertTrue(equals);
	}

	/// INSERT
	/*
	 * Caso de teste para o metodo insert verificando o tamanho da tree
	 */
	@Test
	public void testInsertEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	@Test
	public void testTreeIsLeafSmaller() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3, 1);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[3]]");
		assertTrue(displays);
	}

	@Test
	public void testTreeIsLeafBigger() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(2);
		String info = tree.toString();
		boolean displays = info.equals("[1:[2]]");
		assertTrue(displays);
	}

	@Test
	public void testContains() {
		List<Integer> list = Arrays.asList(39, 59, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(17);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	@Test
	public void testNewRoot() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}

	@Test
	public void testFive() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(8);
		String info = tree.toString();
		boolean displays = info.equals("[5:[8][10][15]]");
		assertTrue(displays);
	}

	@Test
	public void testSix() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(20);
		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}

	@Test
	public void testMenorQMax() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(19);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15:[19][20]]]");
		assertTrue(displays);
	}

	@Test
	public void testEight() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(11);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10:[11]][15]]");
		assertTrue(displays);
	}

	@Test
	public void testSpecialCase() {
		List<Integer> list = Arrays.asList(17, 39, 41, 59, 70, 43, 61);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 4);

		tree.delete(70);
		tree.insert(60);
		String info = tree.toString();
		boolean displays = info.equals("[17:[39][41:[43]][59:[60][61]]]");
		assertTrue(displays);
	}

	/// EMPTY
	/**
	 * Caso de teste para o método isEmpty para uma árvore vazia
	 */
	@Test
	public void testEmpty() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		boolean isEmpty = tree.isEmpty();
		assertTrue(isEmpty);
	}

	@Test
	public void testTreeWithElements() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean isEmpty = tree.isEmpty();
		assertFalse(isEmpty);
	}

	// ISLEAF
	@Test
	public void testEmptyTree() {
		List<Integer> list = Arrays.asList();
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 0);

		boolean isLeaf = tree.isLeaf();
		assertFalse(isLeaf);
	}

	/**
	 * Caso de teste para o método isLeaf para uma árvore com 1 folha
	 */
	@Test
	public void testTreeWithOneElementIsLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean isLeaf = tree.isLeaf();
		assertTrue(isLeaf);
	}

	/**
	 * Caso de teste para o método isLeaf para uma árvore com vários elementos
	 */
	@Test
	public void testTreeWithMoreElements() {
		List<Integer> list = Arrays.asList(39, 17, 55);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		boolean isLeaf = tree.isLeaf();
		assertFalse(isLeaf);
	}

	/// MAX

	/**
	 * Caso de teste para o método max para uma árvore apenas com uma folha
	 */
	@Test
	public void testTreeWithOneElementMaxIsLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(17, 1);
		int max = tree.max();
		assertEquals(17, max, "max value");
	}

	/**
	 * Caso de teste para o método max para uma árvore com vários elementos
	 */
	@Test
	public void testTreeMoreElementsIsLeaf() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 6);
		int max = tree.max();
		assertEquals(85, max, "max value");
	}

	/// MIN

	/**
	 * Caso de teste para o método min para uma árvore apenas com raiz
	 */
	@Test
	public void testTreeWithOneElement() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(17, 1);
		int min = tree.min();
		assertEquals(17, min, "min value");
	}

	/**
	 * Caso de teste para o método min para uma árvore com vários elementos
	 */
	@Test
	public void testTreeMoreElements() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 6);
		int min = tree.min();
		assertEquals(17, min, "min value");
	}

	/// NEXT

	@Test
	public void testEmptyStack() {
		LinkedList<ArrayNTree<Integer>> stack = new LinkedList<ArrayNTree<Integer>>();
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);
		stack.clear();
		Iterator<Integer> it1 = tree.iterator();

		while (it1.hasNext()) {
			it1.next();
		}

		assertThrows(NoSuchElementException.class, () -> {
			it1.next();
		});
	}

	/// SIZE
	/**
	 * Caso de teste para o método size com um elemento
	 */
	@Test
	public void testSizeWithOneElement() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);
		int size = tree.size();
		assertEquals(1, size, "count of elements");
	}

	/**
	 * Caso de teste para o método size com dois elemento
	 */
	@Test
	public void testSizeWithTwoElements() {
		List<Integer> list = Arrays.asList(39, 59);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int size = tree.size();
		assertEquals(2, size, "count of elements");
	}

	@Test
	public void testSizeEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		int size = tree.size();
		assertEquals(1, size, "count of elements");
	}

	/// TOLIST
	@Test
	public void testCompare() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		List<Integer> list = new LinkedList<>();
		for (Integer elem : tree)
			list.add(elem);

		List<Integer> ordered = tree.toList();
		assertEquals(list, ordered, "equal List");
	}

	/// TOSTRING

	/**
	 * Caso de teste para o método toString para uma representação de uma empty tree
	 */
	@Test
	public void testEmptyTreeToString() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		String display = tree.toString();
		boolean equals = display.equals("[]");
		assertTrue(equals);
	}

	@Test
	public void testLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		String display = tree.toString();
		boolean equals = display.equals("[1]");
		assertTrue(equals);
	}

	@Test
	public void testMultipleElemsTree() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45, 37);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 5);

		String display = tree.toString();
		boolean equals = display.equals("[17:[37:[39]][41][45][59][85]]");
		assertTrue(equals);
	}

}
