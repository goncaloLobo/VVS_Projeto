import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;

public class PrimePathCoverageInsert {

	/*
	 * Caso de teste para o metodo insert verificando o tamanho da tree
	 */
	// caminho: 1,2 cobre: (1,2)
	@Test
	public void testPPEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	// caminho: 1,3,5,6,7,8 cobre: (1,3) (3,5) (5,6) (6,7) (7,8) (1,3,5) (3,5,6)
	// (6,7,8)
	@Test
	public void testPPTreeIsLeafSmaller() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3, 1);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[3]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,8 cobre: (5,7,8) (3,5,7)
	@Test
	public void testPPTreeIsLeafBigger() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(2);
		String info = tree.toString();
		boolean displays = info.equals("[1:[2]]");
		assertTrue(displays);
	}

	// caninho: 1,3,4 cobre: (3,4) (1,3,4)
	@Test
	public void testPPContains() {
		List<Integer> list = Arrays.asList(39, 59, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(17);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	// caminho: 1,3,5,6,7,9,10 cobre: (3,5) (5,7) (7,9) (9,10) (5,7,9) (7,9,10)
	@Test
	public void testPPNewRoot() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,9,11,12,13 cobre: (9,11) (11,12) (12,13) (9,11,12)
	// (11,12,13)
	@Test
	public void testPPSix() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(20);
		String info = tree.toString();
		boolean displays = info.equals("[5:[10][15][20]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,6,7,9,10 cobre: (5,6,7) (6,7,9)
	@Test
	public void testPPSeven() {
		List<Integer> list = Arrays.asList(2, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		String info = tree.toString();
		boolean displays = info.equals("[1:[2:[5]][10][15]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,9,11,12,14 cobre: (12,14) (11,12,14) (7,9,11)
	@Test
	public void testPPSpecialCase() {
		List<Integer> list = Arrays.asList(17, 39, 41, 59, 70, 43, 61);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 4);

		tree.delete(70);
		tree.insert(60);
		String info = tree.toString();
		boolean displays = info.equals("[17:[39][41:[43]][59:[60][61]]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,9,11,15,17,18,20 cobre: (18,20) (17,18,20)
	@Test
	public void testPPMenorQMax() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(19);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15:[19][20]]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,9,11,15,17,18,19 cobre: (18,19) (17,18,19)
	@Test
	public void testPPBottom() {
		List<Integer> list = Arrays.asList(17, 39, 41, 59, 70);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(43);
		String info = tree.toString();
		boolean displays = info.equals("[17:[39][41:[43]][59:[70]]]");
		assertTrue(displays);
	}

	// caminho: 1,3,5,7,9,11,15,16 cobre: (15,16) (11,15,16)
	@Test
	public void testPPAtPositionPlusOne() {
		List<Integer> list = Arrays.asList(1, 5, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(10);
		String info = tree.toString();
		boolean displays = info.equals("[1:[5][10][15]]");
		assertTrue(displays);
	}
}
