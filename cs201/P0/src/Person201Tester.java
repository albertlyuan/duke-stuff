import java.util.*;

public class Person201Tester {
    public static void main(String[] args) {

        ArrayList<Person201> list = new ArrayList<>();

        for(int k=0; k < 1000; k++) {
            Person201 p = new Person201();
            if (! list.contains(p)) {
                list.add(p);
            }
        }
        System.out.printf("size = %d\n",list.size());
    }
}
