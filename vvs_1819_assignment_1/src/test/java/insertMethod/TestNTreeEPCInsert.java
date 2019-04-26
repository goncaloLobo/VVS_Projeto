package insertMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeEPCInsert {
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
	public void testTreeIsLeaf() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1,1);
		
		tree.insert(2);
		int size = tree.size();
		assertEquals(2, size, "insert in leaf");
	}
	
	@Test
	public void testContains() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);
		
		tree.insert(39);
		int size = tree.size();
		assertEquals(6, size, "insert");
	}
	
	@Test
	public void testRoot() {
		List<Integer> list = Arrays.asList(39,59,17,85,41,45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list,3);
		
		System.out.println("info antes: " + tree.info());
		tree.insert(10);
		System.out.println("info dps: " + tree.info());
		int size = tree.size();
		assertEquals(7, size, "insert root");
		
	}
	
	// Caso para colocar numa folha
	// Caso para substituir uma raíz
	// Caso para ser o menor dos filhos (-1)
}
