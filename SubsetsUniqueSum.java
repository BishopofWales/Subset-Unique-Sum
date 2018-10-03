import java.util.*;
import java.io.*;
import java.math.*;
import java.nio.*;
public class SubsetsUniqueSum{
	public static void main(String[] args){
		
		final int SIZE = 100;
		final int CHOOSE = 50;
		//int[] elements = {1,3,6,8,10,11};
		int[] elements = new int[SIZE];
		HashSet<Sequence> sequences = new HashSet<Sequence>();
		
		
		//System.out.println("Set size: " + testSet.size());
		
		for(int i = 0; i < elements.length; i++){
			elements[i] = (i + 1) * (i + 1);
			sequences.add(new Sequence(elements[i], i));
			
		}
		
		//Add a black list?
		HashSet<Integer> blackListedSums = new HashSet<Integer>();
		//ghosts keep track of sequences that were eliminated last round but might also cause new sequences to be dupes.
		HashSet<Sequence> ghosts = new HashSet<Sequence>();
		
		for(int count = 1; count < CHOOSE; count++){
			blackListedSums = new HashSet<Integer>();
			
			HashSet<Sequence> oldGhosts = ghosts;
			ghosts = new HashSet<Sequence>();
			
			HashSet<Sequence> blackList = new HashSet<Sequence>();
			
			HashSet<Sequence> oldSequences = sequences;
			sequences = new HashSet<Sequence>();
			
			
		 	for(Sequence oldSequence : oldGhosts){
				for(int k = oldSequence.getIndexOfLVA() + 1; k < elements.length; k++){
					
					Sequence newSequence = new Sequence(oldSequence.getSum() + elements[k], k);
					ghosts.add(newSequence);
					blackListedSums.add(oldSequence.getSum() + elements[k]);
					//blackList.add(newSequence);
					
				}
			}
			
			for(Sequence oldSequence : oldSequences){
				for(int k = oldSequence.getIndexOfLVA() + 1; k < elements.length; k++){
					
					Sequence newSequence = new Sequence(oldSequence.getSum() + elements[k], k);
					
					
					if(blackList.contains(newSequence) || ghosts.contains(newSequence)){
						//ghosts.add(newSequence);
						continue;
					}
					
					if(!sequences.add(newSequence)){
						//added to ghosts
						ghosts.add(newSequence);
						//removed from sequences
						sequences.remove(newSequence);
						//added to blacklist so it is not added again
						blackList.add(newSequence);
						blackListedSums.add(oldSequence.getSum() + elements[k]);
						//System.out.printf("eleminated sequence\n");
					}
					
					/*
					if(!sequences.add(newSequence)){
						blackListedSums.add(oldSequence.getSum() + elements[k]);
					}
					
					*/
					
					
				}
				
			}

			
			System.out.println((count + 1) + " " + sequences.size());
			
		}
		
		
		
		HashSet<Integer> sums = new HashSet<Integer>();
		HashSet<Integer> blackList = new HashSet<Integer>();
		for(Sequence sequence : sequences){
			//System.out.println("Sum: " + sequence.getSum() + "Index of LVA: " + sequence.getIndexOfLVA());
			if(blackList.contains(sequence.getSum()) || blackListedSums.contains(sequence.getSum())){
				
				continue;
			}
			
			if(!sums.add(sequence.getSum())){
				//System.out.println("black listed");
				sums.remove(sequence.getSum());
				blackList.add(sequence.getSum());
				//System.out.printf("eleminated sequence\n");
			}
		}
		System.out.println("Number of unique sums" + " " + sums.size());
		BigInteger sumOfSums = BigInteger.ZERO;
		for(Integer sum : sums){
			//System.out.println("SUM: " + sum);
			byte[] intByteArr = ByteBuffer.allocate(4).putInt(sum).array();
			sumOfSums = sumOfSums.add(new BigInteger(intByteArr));
			
		}
		System.out.println("Sum of sums:" + sumOfSums);
	}	
	
}
class Sequence{

	private int _sum;
	private int _indexOfLVA;
 
	public Sequence(int sum, int indexOfLVA){
		_sum = sum;
		_indexOfLVA = indexOfLVA;
	}
	public int hashCode(){
		return _indexOfLVA + _sum * 100;
	}
	public int getSum(){
		return _sum;
	}
	
	public int getIndexOfLVA(){
		return _indexOfLVA;
	}
	
	@Override
	public boolean equals(Object other){
		if (other == this) return true;
        if (!(other instanceof Sequence)) {
            return false;
        }
		Sequence sequence = (Sequence) other;
		return(this.getIndexOfLVA() == sequence.getIndexOfLVA() && this.getSum() == sequence.getSum());
	}
}