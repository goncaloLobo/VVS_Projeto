package insertMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeInsert {

	/**
	 * Caso de teste para o metodo insert verificando o tamanho da tree
	 */
	@Test
	public void testInsertEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(0);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	@Test
	public void testContains() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	// Substituir o valor da root
	@Test
	public void testInsertSmallest() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);

		tree.insert(1);
		int size = tree.size();
		assertEquals(2, size, "insert");
	}

	@Test
	public void testInsertChildrenBigger() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(5);
		tree.insert(10);
		tree.insert(20);

		int size = tree.size();
		assertEquals(3, size, "insert");
	}

	// insere valor mais pequeno que todos os filhos
	@Test
	public void testInsertSmaller() {
		List<Integer> list = Arrays.asList(1, 5, 10, 15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(4);
		int size = tree.size();
		assertEquals(5, size, "insert between");
	}
	
	@Test
	public void testElementIsLargerChildrenAndFullCapacity() {
		List<Integer> list = Arrays.asList(1,5,10,15);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		
		tree.insert(20);
		System.out.println("dps: " + tree.info());
		int size = tree.size();
		assertEquals(5, size, "insert full capacity");
	}
}
