package insertMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeEPCInsert {

	/*
	 * Caso de teste para o metodo insert verificando o tamanho da tree
	 */
	// caminho: 1,2 cobre: (1,2)
	@Test
	public void testInsertEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	// caminho: 1,3,5,6,7,8 cobre: (1,3) (3,5) (5,6) (6,7) (7,8) (1,3,5) (3,5,6)
	// (6,7,8)
	@Test
	public void testTreeIsLeafSmaller() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3, 1);

		tree.insert(1);
		int size = tree.size();
		assertEquals(2, size, "insert in leaf");
	}

	// caminho: 1,3,5,7,8 cobre: (5,7,8) (3,5,7)
	@Test
	public void testTreeIsLeafBigger() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(2);
		int size = tree.size();
		assertEquals(2, size, "insert in leaf");
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

	// caminho: 1,3,5,7,9,10 cobre: (3,5) (5,7) (7,9) (9,10) (5,7,9) (7,9,10)
	@Test
	public void testNewRoot() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		int size = tree.size();
		assertEquals(4, size, "insert root");
	}

	// caminho: 1,3,5,7,9,11,12,13 cobre: (9,11) (11,12) (12,13) (9,11,12)
	// (11,12,13)
	@Test
	public void testSix() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(20);
		int size = tree.size();
		assertEquals(4, size, "insert");
	}

	// caminho: 1,3,5,6,7,9,10 cobre: (5,6,7) (6,7,9)
	@Test
	public void testSeven() {
		List<Integer> list = Arrays.asList(2, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		int size = tree.size();
		assertEquals(5, size, "insert test seven");
	}

	// caminho: 1,3,5,7,9,11,12,14 cobre: (12,14) (11,12,14) (7,9,11)
	@Test
	public void testSpecialCase() {
		List<Integer> list = Arrays.asList(17, 39, 41, 59, 70, 43, 61);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 4);
		System.out.println("antes: " + tree.info());

		tree.delete(70);
		tree.insert(60);
		int size = tree.size();
		assertEquals(7, size, "insert test eight");
	}

	// caminho: 1,3,5,7,9,11,15,17,18,20 cobre: (18,20) (17,18,20)
	@Test
	public void testMenorQMax() {
		List<Integer> list = Arrays.asList(17, 39, 41, 59, 70);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(43);
		int size = tree.size();
		assertEquals(6, size, "insert");
	}
	
	@Test
	public void testEight() {
		List<Integer> list = Arrays.asList(1,5,10,15,11,12);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(14);
		int size = tree.size();
		assertEquals(7, size, "insert test eight");
	}
}
