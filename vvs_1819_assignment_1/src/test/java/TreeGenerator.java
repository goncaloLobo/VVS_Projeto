import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import sut.ArrayNTree;

public class TreeGenerator extends Generator<ArrayNTree<Integer>> {

	private int min = 5;
	private int max = 40;

	// generator respects `@InRange` annotation, defining the size of string
	public void configure(InRange range) {
		min = range.minInt();
		max = range.maxInt();
	}

	protected TreeGenerator(Class<ArrayNTree<Integer>> type) {
		super(type);
	}

	@Override
	public ArrayNTree<Integer> generate(SourceOfRandomness arg0, GenerationStatus arg1) {
		List<Integer> list = Arrays.asList(5,10,15,20,25,30);
		Collections.shuffle(list); // shuffle da list
		ArrayNTree<Integer> tree = new ArrayNTree<>(3);
		int size = arg0.nextInt(min, max); // tamanho aleatório da árvore
		
		for (int i = 0; i < size; i++) {
			tree.insert(list.get(i));
		}
		
		return tree;
	}

}
