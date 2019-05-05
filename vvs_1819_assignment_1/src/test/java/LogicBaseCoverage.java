import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;

public class LogicBaseCoverage {

	ArrayNTree<Integer> tree;

	/**
	 * P1: c1, where c1: isEmpty()
	 */
	@Test
	public void testLogicP1() {
		tree = new ArrayNTree<>(3);

		tree.insert(1);
		assertEquals(1, tree.size(), "insert");
	}

	/**
	 * P2: c2, where c2: contains(elem)
	 */
	@Test
	public void testLogicP2() {
		List<Integer> list = Arrays.asList(39, 59, 17);
		tree = new ArrayNTree<>(list, 3);

		tree.insert(17);
		assertEquals(3, tree.size(), "insert");
	}

	/**
	 * P3: c3, where c3: data.compareTo(elem) > 0
	 */
	@Test
	public void testLogicP3() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}

	/**
	 * P4: c4, where c4: isLeaf()
	 */
	@Test
	public void testLogicP4() {
		tree = new ArrayNTree<>(3);
		tree.insert(5);

		tree.insert(10);
		String info = tree.toString();
		boolean displays = info.equals("[5:[10]]");
		assertTrue(displays);
	}

	/**
	 * P5: c5, where c5: position == -1
	 */
	@Test
	public void testLogicP5() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);

		tree.insert(8);
		String info = tree.toString();
		boolean displays = info.equals("[5:[8][10][15]]");
		assertTrue(displays);
	}
	
	/**
	 * P6: c6 && c7, where c6: nChildren < capacity; c7: children[position] == null
	 */
	@Test
	public void testLogicP6() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);
		tree.insert(20);

		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}
	
	/**
	 * P7: c8, where c8: elem.compareTo(children[position - 1].max()) > 0
	 */
	@Test
	public void testLogicP7() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);
		tree.insert(20);

		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}
	
	/**
	 * P8: c9 && c10, where c9: nChildren < capacity; c10: elem.compareTo(children[position].max()) > 0
	 */
	@Test
	public void testLogicP8() {
		List<Integer> list = Arrays.asList(1, 5, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(10);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}
	
	/**
	 * P9: c11 || c12, where c11: nChildren == capacity; c12: elem.compareTo(children[position].max()) < 0
	 */
	@Test
	public void testLogicP9() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		tree = new ArrayNTree<>(list, 3);
		tree.insert(19);

		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15:[19][20]]]");
		assertTrue(displays);
	}
	
	/**
	 * P10: c13, where c13: position == capacity
	 */
	@Test
	public void testLogicP10() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		tree = new ArrayNTree<>(list, 3);
		tree.insert(19);

		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15:[19][20]]]");
		assertTrue(displays);
	}
}
