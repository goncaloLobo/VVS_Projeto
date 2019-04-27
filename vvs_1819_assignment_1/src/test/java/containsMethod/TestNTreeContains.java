package containsMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import sut.ArrayNTree;

public class TestNTreeContains {

	@Test
	public void testEmptyTree() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);

		boolean contains = tree.contains(1);
		assertEquals(false, contains, "contains element");
	}

	@Test
	public void testElementAtRoot() {
		ArrayNTree<Integer> tree = new ArrayNTree<>(1, 1);

		boolean contains = tree.contains(1);
		assertEquals(true, contains, "contains element");
	}

	@Test
	public void testTreeDoesNotContainSmaller() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 3);

		boolean contains = tree.contains(5);
		assertEquals(false, contains, "contains element");
	}
	
	@Test
	public void testDoesNotContainLarger() {
		List<Integer> list = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list,3);
		
		boolean contains = tree.contains(90);
		assertEquals(false, contains, "contains element");
	}
	
	@Test
	public void testFindLastChildren() {
		List<Integer> list = Arrays.asList(1,2,3);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list,3);
		
		boolean contains = tree.contains(3);
		assertEquals(true, contains, "contains element");
	}
}
