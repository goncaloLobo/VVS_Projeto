package insertMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreePPInsert {

	// caminho: 1,2 cobre: (1,2)
	@Test
	public void testInsertEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	// caminho: 1,3,5,7,9,11,15,17,18,20 cobre: (18,20) (17,18,20)
	@Test
	public void testMenorQMax() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(19);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15:[19][20]]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,6,7,9,10 cobre: (5,6,7) (6,7,9)
	@Test
	public void testSeven() {
		List<Integer> list = Arrays.asList(2, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[2:[5]][10][15]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,9,11,12,14 cobre: (12,14) (11,12,14) (7,9,11)
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

	// caminho: 1,3,5,7,9,11,12,13 cobre: (9,11) (11,12) (12,13) (9,11,12)
	// (11,12,13)
	@Test
	public void testSix() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(20);
		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,9,10 cobre: (3,5) (5,7) (7,9) (9,10) (5,7,9) (7,9,10)
	@Test
	public void testNewRoot() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}

	// caninho: 1,3,4 cobre: (3,4) (1,3,4)
	@Test
	public void testContains() {
		List<Integer> list = Arrays.asList(39, 59, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(17);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	// caminho: 1,3,5,7,8 cobre: (5,7,8) (3,5,7)
	@Test
	public void testTreeIsLeafBigger() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(2);
		String info = tree.toString();
		boolean displays = info.equals("[1:[2]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,6,7,8 cobre: (1,3) (3,5) (5,6) (6,7) (7,8) (1,3,5) (3,5,6)
	// (6,7,8)
	@Test
	public void testTreeIsLeafSmaller() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3, 1);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[3]]");
		assertTrue(displays);
	}
}
