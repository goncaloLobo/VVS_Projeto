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

	/**
	 * Caso de teste para o metodo insert de um valor existente verificando o
	 * tamanho da tree
	 */
	@Test
	public void testContains() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		tree.insert(1);
		int size = tree.size();
		assertEquals(1, size, "insert");
	}

	/*
	 * Se tiver a inserir valores do genero 10, 20, 21, 30, 31 vou sempre bater no
	 * isLeaf()
	 * Teste com um valor mais pequeno que a root para a substituir
	 */
	@Test
	public void testInsertSmaller() {
		List<Integer> list = Arrays.asList(10, 20, 21);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		tree.insert(5);
		int size = tree.size();
		assertEquals(4, size, "insert");
	}
}
