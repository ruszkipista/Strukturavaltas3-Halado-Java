import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithms {
    public int getElementAppearsMoreThanOnce(List<Integer> numbers) {
        if (numbers == null || numbers.size()<2){
            throw new IllegalArgumentException("Minumum 2 elements required");
        }
        numbers = new ArrayList<>(numbers);
        Collections.sort(numbers);
        for (int i=1; i<numbers.size(); i++) {
            if (numbers.get(i-1)==numbers.get(i)){
                return numbers.get(i);
            }
        }
        throw new IllegalArgumentException("No element appears more than once");
    }
}
