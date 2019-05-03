import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class LogicBaseCoverage {

	ArrayNTree<Integer> tree;

	@Test
	public void testLogicP1() {
		tree = new ArrayNTree<>(1);

		tree.insert(1);
		assertEquals(1, tree.size(), "insert");
	}

	@Test
	public void testLogicP2() {
		List<Integer> list = Arrays.asList(39, 59, 17);
		tree = new ArrayNTree<>(list, 3);

		tree.insert(17);
		assertEquals(3, tree.size(), "insert");
	}

	@Test
	public void testLogicP3() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}

	@Test
	public void testLogicP4() {
		tree = new ArrayNTree<>(1, 1);

		tree.insert(2);
		String info = tree.toString();
		boolean displays = info.equals("[1:[2]]");
		assertTrue(displays);
	}

	@Test
	public void testLogicP5() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);

		tree.insert(8);
		String info = tree.toString();
		boolean displays = info.equals("[5:[8][10][15]]");
		assertTrue(displays);
	}
	
	@Test
	public void testLogicP6() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);
		tree.insert(20);

		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}
	
	@Test
	public void testLogicP7() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		tree = new ArrayNTree<>(list, 3);
		tree.insert(20);

		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}
	
	@Test
	public void testLogicP9() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		tree = new ArrayNTree<>(list, 3);
		tree.insert(19);

		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15:[19][20]]]");
		assertTrue(displays);
	}
	
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
