import java.util.List;

public class LinkStrand implements IDnaStrand {
    private class Node {
        String info;
        Node next;

        public Node(String s) {
            info = s;
            next = null;
        }
    }

    private Node myFirst, myLast; //first and last nodes in linked list
    private long mySize; // size of the total # of characters (nucleic acids) in all the nodes
    private int myAppends; // # of times that .append has been called (# of nodes - 1)

    // THESE UPDATE WITH CHAR AT (below)
    private Node myCurrent; // Last node at position of myIndex (starts @myFirst)
    private int myIndex = 0; // current character accessed (w/ charAt --> starts @0)
    private int myLocalIndex = 0; //character length of the current node (starts @0)

    //REVERSE METHOD BONUSES
    private Node myFirst_rev, myLast_rev; //first and last nodes in linked list

    public LinkStrand() {
        this("");
    }

    /**
     * Create a strand representing s. No error checking is done to see if s
     * represents valid genomic/DNA data.
     *
     * @param s is the source of cgat data for this strand
     */
    public LinkStrand(String s) {

        initialize(s);
    }

    /**
     * Initialize this strand so that it represents the value of source. No
     * error checking is performed.
     */

    @Override
    public long size() {
//        long length_list = 0;
//        while(list != null){
//            list = list.next;
//            length_list++;
//        }
//        // replace statement below with code you write
//        return length_list;

        //aidan i dont think u need ^
        return mySize;
    }


    @Override
    public void initialize(String source) {
        myFirst = new Node(source);
        myLast = myFirst; //initialized last
        myCurrent = myFirst; //initialized current
        mySize = source.length(); //initialized size
        myAppends = 0;

    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        myLast.next = new Node(dna); //add new node to end
        myLast = myLast.next; //update myLast
        mySize += dna.length(); //update size
        myAppends += 1; //update myAppends
        myCurrent = myFirst;
        return this;
    }

    //helper method for append()
    private void frontAppend(String s, LinkStrand b) {
        StringBuilder a = new StringBuilder(s);
        String rev = a.reverse().toString(); //convert reverse stringbuilder to string
        Node newFirst = new Node(rev); //new node
        Node oldFirst = b.myFirst;
        b.myFirst = newFirst; //put new node in front
        b.myFirst.next = oldFirst; //point new myFirst to old myFirst
    }

    @Override
    public IDnaStrand reverse() {
        Node oldlist = myFirst; //old list

        //initialize new list from last node
        StringBuilder a = new StringBuilder(oldlist.info);
        String rev = a.reverse().toString(); //convert reverse stringbuilder to string
        LinkStrand ret = new LinkStrand(rev);
        oldlist = oldlist.next;
        ret.mySize = mySize;
        ret.myAppends = myAppends;
        ret.myCurrent = myFirst;

        //iterate thru old list
        while (oldlist != null) {
            frontAppend(oldlist.info, ret); //adding new node to newlist
            oldlist = oldlist.next;
        }
        return ret;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public char charAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > mySize-1){
            throw new IndexOutOfBoundsException();
        }
        if (index < myIndex) {  //if need to reset myIndex to 0 (if my index is already past where the index were searching is)
            //reset
            myCurrent = myFirst;
            myIndex = 0;
            //iterate until index
            while (index > myIndex) {
                if (myIndex + myCurrent.info.length() - 1 < index) { // if index/char is not in current word/node
                    myIndex += myCurrent.info.length();
                    myCurrent = myCurrent.next;
                } else {
                    //System.out.println(myCurrent.info.charAt(index-myIndex));
                    return myCurrent.info.charAt(index - myIndex); // if index is in the current word/node
                }
            }
        } else if (index > myIndex) {
            //iterate until index
            while (index > myIndex) {
                if (myIndex + myCurrent.info.length() - 1 < index) { // if index/char is not in current word/node
                    // "-1" because you index+info.length is an indice in the next node, -1 makes it so it's not
                    myIndex += myCurrent.info.length();
                    myCurrent = myCurrent.next; // check next node now
                } else {
                    //System.out.println(myCurrent.info.charAt(index-myIndex));
                    return myCurrent.info.charAt(index - myIndex); // if index is in the current word/node
                }
            }
        }
        return myCurrent.info.charAt(index - myIndex); // if index is in the current word/node
    }


    public String toString() {
        //start at first node
        Node list = myFirst;
        StringBuilder output = new StringBuilder();
        while (list!=null) {
            output.append(list.info);
            list = list.next;
        }
        return output.toString();
    }
    // AIdan Rogers
    // ALbert Yuan
    //for testing
    /*
    public static void main(String[] args){
        LinkStrand a = new LinkStrand("aggtccgaaagggtttcccaaagggtttcccagaggtccgttccggttaaggagagagagagagtttbcdefgh");
        //a.append("45789");
        //a.append("0123456789");
        //a.reverse();
        System.out.println(a.charAt(74));
        String b = a.toString();
        System.out.println(b);
    }

     */

}
