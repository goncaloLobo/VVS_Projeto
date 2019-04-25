package infoMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import sut.ArrayNTree;

public class TestNTreeInfo {
	@Test
	public void testEmptyTree() {
		List<Integer> list1 = Arrays.asList(39, 59, 17, 85, 41, 45);
		ArrayNTree<Integer> tree = new ArrayNTree<>(list1, 6);
		String infoTree = tree.info();
		
		int size = tree.size();
		int height = tree.height();
		int countLeaves = tree.countLeaves();
		String info = tree + ", size: " + size + ", height: " + height + ", nLeaves: " + countLeaves;
		
		boolean equals = info.equals(infoTree);
		assertEquals(true, equals, "equals");
	}
}
