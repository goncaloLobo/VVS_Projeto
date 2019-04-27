package equalsMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeEquals {

	/**
	 * Caso de teste para o método equals a comparar a árvore consigo própria
	 */
	@Test
	public void testComparingItself() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(tree);
		assertEquals(true, equals, "equals");
	}

	/**
	 * Caso de teste para o método equals a comparar duas árvores iguais
	 */
	@Test
	public void testEqualsTrees() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(tree2);
		assertEquals(true, equals, "equals");
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
		assertEquals(false, equals, "not equal trees");
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
		assertEquals(false, equals, "not equal trees");
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
		assertEquals(false, equals, "not equal trees");
	}

	/**
	 * Caso de teste para o método equals a comparar uma árvore com um outro objeto
	 */
	@Test
	public void testObjectOther() {
		List<Integer> list1 = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 3);

		boolean equals = tree.equals(0);
		assertEquals(false, equals, "equals");
	}

}