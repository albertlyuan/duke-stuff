import java.util.PriorityQueue;

/**
 * Although this class has a history of several years,
 * it is starting from a blank-slate, new and clean implementation
 * as of Fall 2018.
 * <P>
 * Changes include relying solely on a tree for header information
 * and including debug and bits read/written information
 * 
 * @author Owen Astrachan
 */

public class HuffProcessor {

	public static final int BITS_PER_WORD = 8;
	public static final int BITS_PER_INT = 32;
	public static final int ALPH_SIZE = (1 << BITS_PER_WORD);
	public static final int PSEUDO_EOF = ALPH_SIZE;
	public static final int HUFF_NUMBER = 0xface8200;
	public static final int HUFF_TREE = HUFF_NUMBER | 1;

	private final int myDebugLevel;

	public static final int DEBUG_HIGH = 4;
	public static final int DEBUG_LOW = 1;

	public HuffProcessor() {
		this(0);
	}

	public HuffProcessor(int debug) {
		myDebugLevel = debug;
	}

	/**
	 * Compresses a file. Process must be reversible and loss-less.
	 *
	 * @param in  Buffered bit stream of the file to be compressed.
	 * @param out Buffered bit stream writing to the output file.
	 */
	public void compress(BitInputStream in, BitOutputStream out) {
		int[] counts = readForCounts(in);
		HuffNode root = makeTreeFromCounts(counts);
		String[] codings = makeCodingsFromTree(root);

		out.writeBits(BITS_PER_INT, HUFF_TREE);
		writeHeader(root, out);
		in.reset();
		writeCompressedBits(codings, in, out);
		out.close();

	}

	private int[] readForCounts(BitInputStream in) {
		//create dictionary for characters. idx = character, freq[idx] is frequency
		int[] freq = new int[ALPH_SIZE + 1];

		while (true) {
			int idx = in.readBits(BITS_PER_WORD);
			if (idx == -1) {
				break;
			}
			freq[idx] += 1; // increment the letter (which is the binary idx) by one

		}
		freq[PSEUDO_EOF] = 1;
		return freq;
	}

	private HuffNode makeTreeFromCounts(int[] freq) {
		PriorityQueue<HuffNode> pq = new PriorityQueue<>();

		//adds character to priority queue
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] > 0) { // whenever there's a letter add a huffnode as said letter as a leaf
				pq.add(new HuffNode(i, freq[i], null, null));
			}
		}
		//creates hufftree
		while (pq.size() > 1) {
			HuffNode left = pq.remove();
			HuffNode right = pq.remove();
			HuffNode t = new HuffNode(0, left.myWeight + right.myWeight, left, right);
			pq.add(t);
		}
		HuffNode root = pq.remove();
		return root;

	}

	private String[] makeCodingsFromTree(HuffNode root) {
		String[] encodings = new String[ALPH_SIZE + 1];
		codingHelper(root, "", encodings);
		return encodings;
	}

	private void codingHelper(HuffNode root, String path, String[] encodings) {
		if (root.myLeft == null && root.myRight == null) {
			encodings[root.myValue] = path;
			return;
		}
		if (root.myLeft == null) {
			codingHelper(root.myRight, path + "1", encodings);
		} else if (root.myRight == null) {
			codingHelper(root.myLeft, path + "0", encodings);
		} else {
			codingHelper(root.myLeft, path + "0", encodings);
			codingHelper(root.myRight, path + "1", encodings);
		}
	}

	private void writeHeader(HuffNode root, BitOutputStream out) {
		if (root == null){return;}
		//preorder
		if (root.myLeft == null && root.myRight == null){
			out.writeBits(1,1);
			out.writeBits(BITS_PER_WORD+1, root.myValue);
		}else{
			out.writeBits(1,0);
		}
		// find the letter, print it out (+1 for pseudo EOF)
		writeHeader(root.myLeft, out);
		writeHeader(root.myRight,out);
	}

	private void writeCompressedBits(String[] codings, BitInputStream in, BitOutputStream out) {
		while(true) {
			int current_bits = in.readBits(BITS_PER_WORD);
			if (current_bits == -1){
				break;
			}
			String code = codings[current_bits];
			out.writeBits(code.length(), Integer.parseInt(code, 2));
		}
		String code2 = codings[PSEUDO_EOF];
		out.writeBits(code2.length(), Integer.parseInt(code2, 2));
	}

	/**
	 * Decompresses a file. Output file must be identical bit-by-bit to the
	 * original.
	 *
	 * @param in  Buffered bit stream of the file to be decompressed.
	 * @param out Buffered bit stream writing to the output file.
	 */
	public void decompress(BitInputStream in, BitOutputStream out) {

		int magic = in.readBits(BITS_PER_INT);
		if (magic != HUFF_TREE) {
			throw new HuffException("invalid magic number " + magic);
		}
		//create tree
		HuffNode root = readTree(in);
		HuffNode current = root;
		//while bitinputstream maps to NOT PESEUDO_EOF, output map value
		while (true) {
			int bits = in.readBits(1); //read the next bit

			if (bits == -1) {
				throw new HuffException("bad input, no PSEUDO_EOF");
			}else {
				//tree traverse
				if (bits == 0){
					current = current.myLeft;
				}else {
					current = current.myRight;
				}

				//check if leaf node
				if (current.myLeft == null && current.myRight == null) {
					if (current.myValue == PSEUDO_EOF)
						break;
					else {
						out.writeBits(BITS_PER_WORD,current.myValue);
						current = root; // start back after leaf
					}
				}
			}
		}
		out.close();
	}


	private HuffNode readTree(BitInputStream in) {
		int bit = in.readBits(1);
		if (bit == -1) throw new HuffException("invalid magic number " + bit);
		if (bit == 0) {
			HuffNode left = readTree(in);
			HuffNode right = readTree(in);
			return new HuffNode(0, 0, left, right);
		} else {
			int value = in.readBits(BITS_PER_WORD + 1);
			return new HuffNode(value, 0, null, null);
		}
	}
}