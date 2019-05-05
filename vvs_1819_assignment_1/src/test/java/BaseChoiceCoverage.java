import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sut.ArrayNTree;

public class BaseChoiceCoverage {

	/**
	 * Caso de teste: [!tree1empty, !tree2empty, !tree2null, empty]
	 */
	@Test
	public void testCaso1() {
		List<Integer> list = Arrays.asList(10, 20, 25, 22, 30);
		List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
		ArrayNTree<Integer> tree1 = new ArrayNTree<>(list, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list2, 3);

		assertFalse(tree1.equals(tree2));
	}

	/**
	 * Caso de teste: [tree1empty, !tree2empty, !tree2null, empty]
	 */
	@Test
	public void testCaso2() {
		List<Integer> list = Arrays.asList(10, 20, 25, 22, 30);
		ArrayNTree<Integer> tree1 = new ArrayNTree<>(3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list, 3);

		assertThrows(NullPointerException.class, () -> {
			tree1.equals(tree2);
		});
	}

	/**
	 * Caso de teste: [!tree1empty, tree2empty, !tree2null, empty]
	 */
	@Test
	public void testCaso3() {
		List<Integer> list = Arrays.asList(10, 20, 25, 22, 30);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(3);
		ArrayNTree<Integer> tree1 = new ArrayNTree<>(list, 3);

		assertFalse(tree1.equals(tree2));
	}

	/**
	 * Caso de teste: [!tree1empty, !tree2empty, !tree2null, partial]
	 */
	@Test
	public void testCaso5() {
		List<Integer> list = Arrays.asList(10, 20, 25);
		List<Integer> list2 = Arrays.asList(10, 20, 22, 25, 30);
		ArrayNTree<Integer> tree1 = new ArrayNTree<>(list, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list2, 3);

		assertFalse(tree1.equals(tree2));
	}

	/**
	 * Caso de teste: [!tree1empty, !tree2empty, !tree2null, full]
	 */
	@Test
	public void testCaso6() {
		List<Integer> list = Arrays.asList(10, 20, 25, 22, 30);
		List<Integer> list2 = Arrays.asList(30, 22, 25, 10, 20);
		ArrayNTree<Integer> tree1 = new ArrayNTree<>(list, 3);
		ArrayNTree<Integer> tree2 = new ArrayNTree<>(list2, 3);

		// árvores são iguais, interseção full
		assertTrue(tree1.equals(tree2));
	}
}