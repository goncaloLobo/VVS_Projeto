import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

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
	/**
	 * Caso de teste para o método contains de um elemento numa árvore vazia
	 */
	@Test
	public void testContainsEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);

		boolean contains = tree.contains(1);
		assertFalse(contains);
	}

	/**
	 * Caso de teste para o método contains de um elemento que é a raíz da árvore
	 */
	@Test
	public void testContainsElementAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean contains = tree.contains(1);
		assertTrue(contains);
	}

	/**
	 * Caso de teste para o método contains de um elemento inexistente e mais
	 * pequeno que os elementos na árvore
	 */
	@Test
	public void testContainsNotContainSmaller() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(5);
		assertFalse(contains);
	}

	/**
	 * Caso de teste para o método contains de um elemento existente
	 */
	@Test
	public void testContainsEqual() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(85);
		assertTrue(contains);
	}

	/**
	 * Caso de teste para o método equals de um elemento superior a todos na árvore
	 * inexistente
	 */
	@Test
	public void testContainsNotContainLarger() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(90);
		assertFalse(contains);
	}

	/**
	 * Caso de teste para o método equals de um elemento existente nos childrens
	 */
	@Test
	public void testContainsInChildren() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(45);
		assertTrue(contains);
	}

	/// COUNT LEAVES
	/**
	 * Caso de teste para o método count leaves com uma árvore apenas com raiz
	 */
	@Test
	public void testCountLeavesTreeWithOneElement() {
		List<Integer> list = Arrays.asList(17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 1);

		int leaves = tree.countLeaves();
		assertEquals(1, leaves, "count leaves");
	}

	/**
	 * Caso de teste para o método count leaves com árvore com vários elementos
	 */
	@Test
	public void testCountLeavesTreeMoreElements() {
		List<Integer> list = Arrays.asList(39, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int leaves = tree.countLeaves();
		assertEquals(1, leaves, "count leaves");
	}

	/// DELETE
	/**
	 * Caso de teste para o método delete de um elemento de uma árvore sem elementos
	 */
	@Test
	public void testDeleteEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.delete(1);
		int size = tree.size();
		assertEquals(0, size, "delete empty");
	}

	/**
	 * Caso de teste para o método delete de delete da raíz
	 */
	@Test
	public void testDeleteRoot() {
		List<Integer> list = Arrays.asList(1, 2);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		tree.delete(1);
		int size = tree.size();
		assertEquals(1, size, "delete root");
	}

	/**
	 * Caso de teste para o método delete de um elemento inexistente mais pequeno
	 * que a raíz
	 */
	@Test
	public void testDeleteSmallerThanRoot() {
		List<Integer> list = Arrays.asList(2, 3, 4);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(1);
		int size = tree.size();
		assertEquals(3, size, "delete root");
	}

	/**
	 * Caso de teste para o método delete de um elemento maior e inexistente
	 */
	@Test
	public void testDeleteBiggerElement() {
		List<Integer> list = Arrays.asList(1, 2, 3);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(4);
		int size = tree.size();
		assertEquals(3, size, "delete bigger");
	}

	/**
	 * Caso de teste para o método delete de um elemento inexistente e mais pequeno
	 * que os filhos
	 */
	@Test
	public void testDeleteSmallerElement() {
		List<Integer> list = Arrays.asList(1, 3, 4);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.delete(2);
		int size = tree.size();
		assertEquals(3, size, "delete smaller");
	}

	/**
	 * Caso de teste para o método delete com foco no método privado Compact()
	 */
	@Test
	public void testDeleteCompact() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 4);

		tree.delete(6);
		int size = tree.size();
		assertEquals(7, size, "delete compact");
	}

	/// EQUALS
	/**
	 * Caso de teste para o método equals a comparar duas árvores vazias
	 */
	@Test
	public void testEqualsTwoEmptyTrees() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(1);
		assertThrows(NullPointerException.class, () -> {
			tree.equals(tree2);
		});
	}

	/**
	 * Caso de teste para o método equals a comparar duas árvores com a mesma
	 * referência
	 */
	@Test
	public void testEqualsTreesSameReferences() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		ArrayNTree<Integer> tree2 = tree;

		boolean equals = tree2.equals(tree);
		assertTrue(equals);
	}

	/**
	 * Caso de teste para o método equals a comparar duas árvores iguais
	 */
	@Test
	public void testEqualsEqualTrees() {
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
	public void testEqualsFirstTreeBigger() {
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
	public void testEqualsSecondTreeBigger() {
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
	public void testEqualsNotEqualTrees() {
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
	public void testEqualsObjectOther() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(0);
		assertFalse(equals);
	}

	/**
	 * Caso de teste para o método equals para uma árvore cujos elementos intersetam
	 * parte dos elementos de uma árvore maior
	 */
	@Test
	public void testEqualsTreeContainsOther() {
		List<Integer> list1 = Arrays.asList(10, 20, 21, 30, 35, 40);
		List<Integer> list2 = Arrays.asList(10, 21, 30, 35);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list2, 3);

		boolean equals = tree.equals(tree2);
		assertFalse(equals);
	}

	/// HEIGHT
	/**
	 * Caso de teste para o método height para uma árvore vazia
	 */
	@Test
	public void testHeightEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);

		int height = tree.height();
		assertEquals(0, height, "height of tree");
	}

	/**
	 * Caso de teste para o método height de uma árvore com vários elementos
	 */
	@Test
	public void testHeightMultipleElementsTree() {
		List<Integer> list = Arrays.asList(39, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int height = tree.height();
		assertEquals(2, height, "height of tree");
	}

	/// INFO
	/**
	 * Caso de teste para o método info de uma árvore com vários elementos
	 */
	@Test
	public void testInfoTree() {
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

	/**
	 * Caso de teste para o método info de uma árvore vazia
	 */
	@Test
	public void testInfoEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		String infoTree = tree.info();

		int size = tree.size();
		int height = tree.height();
		int countLeaves = tree.countLeaves();
		String info = tree + ", size: " + size + ", height: " + height + ", nLeaves: " + countLeaves;

		boolean equals = info.equals(infoTree);
		assertTrue(equals);
	}

	/// INSERT
	/**
	 * Caso de teste para o metodo insert verificando o tamanho da tree
	 */
	@Test
	public void testInsertEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1);
		tree.insert(1);

		int size = tree.size();
		assertEquals(1, size, "insert empty tree");
	}

	/**
	 * Caso de teste para o método insert de um elemento numa leaf verificando a
	 * disposição da árvore
	 */
	@Test
	public void testInsertTreeIsLeafSmaller() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(3);
		tree.insert(1);

		String info = tree.toString();
		boolean displays = info.equals("[1:[3]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método insert de uma leaf verificando depois o tamanho
	 * da árvore
	 */
	@Test
	public void testInsertTreeIsLeafBigger() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(1);
		tree.insert(2);

		String info = tree.toString();
		boolean displays = info.equals("[1:[2]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método contains que verifica que o elemento a fazer
	 * insert já existe na árvore, não alterando o seu tamanho
	 */
	@Test
	public void testInsertContains() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(39);
		tree.insert(59);
		tree.insert(17);

		tree.insert(17);
		int size = tree.size();
		assertEquals(3, size, "insert contains element");
	}

	/**
	 * Caso de teste para o método insert introduzindo uma nova raíz, verificando
	 * depois a disposição da árvore
	 */
	@Test
	public void testInsertNewRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(5);
		tree.insert(10);
		tree.insert(15);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método de insert introduzindo um elemento maior e menor
	 * que alguns dos nós filhos, verificando depois a disposição da árvore
	 */
	@Test
	public void testInsertAtPositionPlusOne() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(1);
		tree.insert(5);
		tree.insert(15);

		tree.insert(10);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método insert introduzindo um valor mais pequeno que os
	 * children, verificando depois a disposição da árvore
	 */
	@Test
	public void testInsertSmallerThanChildren() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(5);
		tree.insert(10);
		tree.insert(15);

		tree.insert(8);
		String info = tree.toString();
		boolean displays = info.equals("[5:[8][10][15]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método insert introduzindo um elemento maior que os
	 * filhos, verificando depois a disposição da árvore
	 */
	@Test
	public void testInsertBiggerThanChildren() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(5);
		tree.insert(10);
		tree.insert(15);

		tree.insert(20);
		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método insert introduzindo um elemento mais pequeno que
	 * o maior elemento da árvore
	 */
	@Test
	public void testInsertMenorQMax() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(1);
		tree.insert(5);
		tree.insert(10);
		tree.insert(15);
		tree.insert(20);

		tree.insert(19);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15:[19][20]]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método insert com a introdução de um médoto mais pequeno
	 * que alguns dos childrens
	 */
	@Test
	public void testInsertSmallerThanLastChild() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(1);
		tree.insert(5);
		tree.insert(10);
		tree.insert(15);

		tree.insert(11);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10:[11]][15]]");
		assertTrue(displays);
	}

	/**
	 * Caso de teste para o método insert, eliminando um dos filhos superiores e
	 * introduzindo depois um novo filho mais pequeno que o elemento máximo
	 */
	@Test
	public void testInsertSpecialCase() {
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

	/**
	 * Caso de teste para o método isEmpty para uma árvore com vários elementos
	 */
	@Test
	public void testEmptyTreeWithElements() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean isEmpty = tree.isEmpty();
		assertFalse(isEmpty);
	}

	// ISLEAF
	/**
	 * Caso de teste para o método isLeaf para uma árvore vazia
	 */
	@Test
	public void testIsLeafEmptyTree() {
		List<Integer> list = Arrays.asList();
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 0);

		boolean isLeaf = tree.isLeaf();
		assertFalse(isLeaf);
	}

	/**
	 * Caso de teste para o método isLeaf para uma árvore com 1 folha
	 */
	@Test
	public void testIsLeafTreeWithOneElementIsLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(1);

		boolean isLeaf = tree.isLeaf();
		assertTrue(isLeaf);
	}

	/**
	 * Caso de teste para o método isLeaf para uma árvore com vários elementos
	 */
	@Test
	public void testIsLeafTreeWithMoreElements() {
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
	public void testMaxLeafTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(17, 1);
		int max = tree.max();
		assertEquals(17, max, "max value");
	}

	/**
	 * Caso de teste para o método max para uma árvore com vários elementos
	 */
	@Test
	public void testMaxElementOfTree() {
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
	public void testMinLeafTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(17, 1);
		int min = tree.min();
		assertEquals(17, min, "min value leaf tree");
	}

	/**
	 * Caso de teste para o método min para uma árvore com vários elementos
	 */
	@Test
	public void testMinTreeMoreElements() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 6);
		int min = tree.min();
		assertEquals(17, min, "min value");
	}

	/// NEXT
	/**
	 * Caso de teste para o método next
	 */
	@Test
	public void testNextEmptyStack() {
		LinkedList<ArrayNTree<Integer>> stack = new LinkedList<ArrayNTree<Integer>>();
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		stack.clear();
		Iterator<Integer> it1 = tree.iterator();

		while (it1.hasNext()) {
			it1.next();
		}

		assertThrows(NoSuchElementException.class, () -> {
			it1.next();
		});
	}

	/// HASNEXT
	/**
	 * Caso de teste para o método hasNext com uma stack com pelo menos 1 árvore
	 */
	@Test
	public void testHasNextTrue() {
		LinkedList<ArrayNTree<Integer>> stack = new LinkedList<ArrayNTree<Integer>>();
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		stack.push(tree);

		Iterator<Integer> it1 = tree.iterator();
		assertEquals(true, it1.hasNext(), "next");
	}

	/**
	 * Caso de teste para o método hasNext com uma stack sem árvores
	 */
	@Test
	public void testHasNextFalse() {
		LinkedList<ArrayNTree<Integer>> stack = new LinkedList<ArrayNTree<Integer>>();
		assertEquals(0, stack.size(), "next");
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

	/**
	 * Caso de teste para o método size para uma árvore vazia
	 */
	@Test
	public void testSizeEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		int size = tree.size();
		assertEquals(0, size, "size");
	}

	/// TOLIST
	/**
	 * Caso de teste para o método toList que verifica a igualdade de listas
	 */
	@Test
	public void testToListCompare() {
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
	public void testToStringEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);

		String display = tree.toString();
		boolean equals = display.equals("[]");
		assertTrue(equals);
	}

	/**
	 * Caso de teste para o método toString para a representação de uma leaf
	 */
	@Test
	public void testToStringLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		String display = tree.toString();
		boolean equals = display.equals("[1]");
		assertTrue(equals);
	}

	/**
	 * Caso de teste para o método toString para a representação de uma árvore com
	 * vários elementos
	 */
	@Test
	public void testToStringMultipleElems() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45, 37);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 5);

		String display = tree.toString();
		boolean equals = display.equals("[17:[37:[39]][41][45][59][85]]");
		assertTrue(equals);
	}
}
