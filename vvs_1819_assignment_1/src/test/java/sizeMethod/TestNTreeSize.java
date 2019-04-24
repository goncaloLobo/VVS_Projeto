package sizeMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeSize {

	/**
	 * Caso de teste para o método size com um elemento
	 */
	@Test
	public void testSizeWithOneElement() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);
		int size = tree.size();
		assertEquals(1, size, "count of elements");
	}

	/**
	 * Caso de teste para o método size com dois elemento
	 */
	@Test
	public void testSizeWithTwoElements() {
		List<Integer> list = Arrays.asList(39, 59);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int size = tree.size();
		assertEquals(2, size, "count of elements");
	}
}
