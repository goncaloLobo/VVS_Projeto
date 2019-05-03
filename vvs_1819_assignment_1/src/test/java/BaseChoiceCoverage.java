import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class BaseChoiceCoverage {

	/**
	 * Caso de teste: [tree1empty, tree2empty, !tree2null, empty]
	 */
	@Test
	public void testCaso1() {
		ArrayNTree<Integer> emptyTree1 = new ArrayNTree<>(1);
		ArrayNTree<Integer> emptyTree2 = new ArrayNTree<>(1);
		assertThrows(NullPointerException.class, () -> {emptyTree1.equals(emptyTree2);});
	}
	
	/**
	 * Caso de teste: [!tree1empty, tree2empty, !tree2null, empty]
	 */
	@Test
	public void testCaso2() {
		ArrayNTree<Integer> emptyTree = new ArrayNTree<>(1);
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(5);
		tree.insert(10);
		tree.insert(15);
		tree.insert(1);
		
		assertFalse(tree.equals(emptyTree));
	}
	
	/**
	 * Caso de teste: [tree1empty, !tree2empty, !tree2null, empty]
	 */
	@Test
	public void testCaso3() {
		ArrayNTree<Integer> emptyTree = new ArrayNTree<>(1);
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		tree.insert(5);
		tree.insert(10);
		tree.insert(15);
		tree.insert(1);
		
		assertFalse(emptyTree.equals(tree));
	}
}
