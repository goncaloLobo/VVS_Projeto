package heightMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeHeightMethod {
	@Test
	public void testEmptyTree() {
		List<Integer> list = Arrays.asList();
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 0);

		int height = tree.height();
		assertEquals(0, height, "height of tree");
	}
	
	@Test
	public void testMoreElements() {
		List<Integer> list = Arrays.asList(39, 17);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list, 2);

		int height = tree.height();
		assertEquals(2, height, "height of tree");
	}
}
