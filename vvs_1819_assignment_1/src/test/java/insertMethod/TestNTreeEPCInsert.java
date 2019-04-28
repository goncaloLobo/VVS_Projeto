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

	// caminho: 1,4,5
	@Test
	public void testTreeIsLeafSmaller() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3, 1);

		tree.insert(1);
		int size = tree.size();
		assertEquals(2, size, "insert in leaf");
	}
	
	//caminho: 1,5
	@Test
	public void testTreeIsLeafBigger() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(2);
		int size = tree.size();
		assertEquals(2, size, "insert in leaf");
	}

	// (1,3)
	@Test
	public void testContains() {
		List<Integer> list = Arrays.asList(39, 59, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(17);
		int size = tree.size();
		assertEquals(3, size, "insert");
	}
	
	// caminho: 1,4,6,7,1,6,11
	@Test
	public void testNewRoot() {
		List<Integer> list = Arrays.asList(5,10,15);
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
	@Test
	public void testSix() {
		List<Integer> list = Arrays.asList(5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(20);
		int size = tree.size();
		assertEquals(4, size, "insert");
	}

	//caminho: 1,6,12,13,1,6,7,1,6,8,9
	@Test
	public void testSeven() {
		List<Integer> list = Arrays.asList(2, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		System.out.println("antes: " + tree.info());
		tree.insert(1);
		System.out.println("dpsss: " + tree.info());
		//tree.insert(19);
		int size = tree.size();
		assertEquals(5, size, "insert test seven");
	}
	
	//caminho: 1,6,12,14,1,5
	@Test
	public void testMenorQMax() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15, 20);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(19);
		int size = tree.size();
		assertEquals(5, size, "insert test seven");
	}

	@Test
	public void testEight() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(11);
		int size = tree.size();
		assertEquals(7, size, "insert test eight");
	}
}
