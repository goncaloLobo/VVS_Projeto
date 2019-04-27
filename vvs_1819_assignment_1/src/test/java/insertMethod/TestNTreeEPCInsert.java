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
	// (1,2)
	@Test
	public void testInsertEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	// (1,5)
	@Test
	public void testTreeIsLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(2);
		int size = tree.size();
		assertEquals(2, size, "insert in leaf");
	}

	// (1,3)
	@Test
	public void testContains() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(39);
		int size = tree.size();
		assertEquals(6, size, "insert");
	}

	// (1,4)
	@Test
	public void testRoot() {
		List<Integer> list = Arrays.asList(2);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(1);
		int size = tree.size();
		assertEquals(2, size, "insert root");
	}

	// caminho: 1,6,7,1,6,11
	// (1,6) (6,7) (7,1) (6,11) (1,6,7) (6,7,1) (7,1,6) (1,6,11)
	@Test
	public void testFive() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(8);
		int size = tree.size();
		assertEquals(4, size, "insert");
	}

	// caminho: 1,6,8,9
	// (1,6) (6,8) (8,9) (1,6,8) (6,8,9)
	@Test
	public void testSix() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(20);
		int size = tree.size();
		assertEquals(4, size, "insert");
	}

	@Test
	public void testSeven() {
		List<Integer> list = Arrays.asList(1, 5, 10);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		System.out.println("insert antes: " + tree.info());
		tree.insert(9);
		System.out.println("insert dpsss: " + tree.info());
		int size = tree.size();
		assertEquals(5, size, "insert test seven");
	}

}
