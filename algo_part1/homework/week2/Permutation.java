import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> test = new RandomizedQueue<>();
        while(!StdIn.isEmpty()){
            String s = StdIn.readString();
            test.enqueue(s);
            //System.out.println(s);
        }
        for(int i = 0;i < k; i++){
            System.out.println(test.dequeue());
        }
        //System.out.println(k);
    }
}
