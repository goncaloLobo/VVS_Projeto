import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

import sut.ArrayNTree;

@RunWith(JUnitQuickcheck.class)
public class ArrayNTreeQuickCheck {
	
	@Property(trials = 15)
	public void testShuffle(@InRange(minInt = 5, maxInt = 20) @From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		ArrayNTree<Integer> newTree = new ArrayNTree<>(3);
		List<Integer> lista = tree.toList();
		Collections.shuffle(lista);

		for (Integer elem : lista) {
			newTree.insert(elem);
		}

		assertTrue(newTree.equals(tree));
		}

	@Property(trials = 15)
	public void testEmpty(@InRange(minInt = 5, maxInt = 20) @From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		List<Integer> lista = tree.toList();

		for (Integer elem : lista) {
			tree.delete(elem);
		}

		assertEquals(1, tree.size(), "size");
	}

	@Property(trials = 15)
	public void testInsertAndRemoveElem(
			@InRange(minInt = 5, maxInt = 20) @From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		ArrayNTree<Integer> newTree = new ArrayNTree<Integer>(3);
		List<Integer> list = tree.toList();

		for (Integer elem : list) {
			newTree.insert(elem);
		}

		newTree.insert(50);
		newTree.delete(50);

		assertTrue(newTree.equals(tree));
	}

	@Property(trials = 15)
	public void insertAllElementsAgain(
			@InRange(minInt = 5, maxInt = 20) @From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		List<Integer> lista = tree.toList();
		int size = tree.size(); // tamanho da arvore - numero de elementos
		for (Integer elem : lista) {
			tree.insert(elem);
		}

		assertEquals(size, tree.size(), "insert all elements again");
	}

	@Property(trials = 15)
	public void insertExistingElement(@InRange(minInt = 5, maxInt = 20) @From(TreeGenerator.class) ArrayNTree<Integer> tree) {
		List<Integer> lista = tree.toList();
		int sizeL = lista.size();
		int size = tree.size(); // tamanho da arvore antes de tentar inserir
		Random random = new Random();
		
		int index = random.nextInt(sizeL); //obter valor aleatório da lista
		tree.insert(lista.get(index));
		
		assertEquals(size, tree.size(), "insert existing element");
	}

}
