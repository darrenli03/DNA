import java.util.Deque;
import java.util.LinkedList;

public class LinkStrand implements IDnaStrand {

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    public LinkStrand() {
        this("");
    }

    public LinkStrand(String s) {
        initialize(s);
    }

    private class Node {
        private String info;
        private Node next;

        private Node(String s) {
            info = s;

        }

        private Node(String s, Node nextNode) {
            info = s;
            next = nextNode;

        }


    }

    @Override
    public long size() {
        return mySize;
    }

    @Override
    public void initialize(String source) {

        myAppends = 0;
        mySize = source.length();
        myIndex = 0;
        myLocalIndex = 0;
        myFirst = new Node(source, null);
        myLast = myFirst;
        myCurrent = myFirst;

    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {

        myCurrent = myLast;
        myCurrent.next = new Node(dna);
        myCurrent = myCurrent.next;
        myLast = myCurrent;
        mySize += dna.length();
        myAppends++;

        return this;
    }

    @Override
    public IDnaStrand reverse() {
        Deque<String> stack = new LinkedList<String>();

        LinkStrand reversed = new LinkStrand();
        String myLastReversed = new StringBuilder(myLast.info).reverse().toString();
        reversed.myFirst = new Node(myLastReversed);
        reversed.mySize = mySize;
        reversed.myCurrent = reversed.myFirst;
        reversed.myLast = myFirst;
        reversed.myAppends = 0;
        reversed.myIndex = 0;
        reversed.myLocalIndex = 0;

        if (myFirst.next != null) {
            Node stackPopulate = myFirst;
            while (stackPopulate != null) {
                stack.addFirst(stackPopulate.info);
                stackPopulate = stackPopulate.next;
            }
            //take the top value off the stack because it is already added to the reversed.myFirst
            stack.removeFirst();

            while (!stack.isEmpty()) {
                String nextReversed = new StringBuilder(stack.removeFirst()).reverse().toString();
                reversed.myCurrent.next = new Node(nextReversed);
                reversed.myCurrent = reversed.myCurrent.next;
            }

        }
        reversed.myCurrent = reversed.myFirst;
        return reversed;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public char charAt(int index) throws IndexOutOfBoundsException {

        if(index > mySize || index < 0) throw new IndexOutOfBoundsException("index " + index + " is not valid for LinkStrand size " + mySize);
        StringBuilder sb = new StringBuilder();

        if(sb.length() > index){
            return sb.charAt(index);
        }
        myCurrent = myFirst;
        while(sb.length() <= index){
            sb.append(myCurrent.info);
            myCurrent = myCurrent.next;
        }
        return sb.charAt(index);

        //figure out why this code throws an error
/*        System.out.println("myCurrent.info is: " + myCurrent.info);
        if(index >= mySize || index < 0) throw new IndexOutOfBoundsException("index " + index + " is not valid for LinkStrand size " + mySize);

        if(index == myIndex) {
            return myCurrent.info.charAt(myLocalIndex);

        } else if(index > myIndex){//case for when index is past where myIndex is currently
            //checks if adding the characters from this node to myIndex would exceed the target index. If not, we are not at the correct node and need to go check the next one
            System.out.println(myCurrent.info.length());
            while(myIndex + myCurrent.info.length() < index){
                myIndex += myCurrent.info.length();
                myCurrent = myCurrent.next;
            }
            //once we've iterated myCurrent to be on the correct node
            myLocalIndex = index - myIndex;
            return myCurrent.info.charAt(myLocalIndex);
        } else { // case occurs when index < myIndex
            myIndex = 0;
            myCurrent = myFirst;

            while(myIndex + myCurrent.info.length() < index){
                myIndex += myCurrent.info.length();
                myCurrent = myCurrent.next;
            }
            //once we've iterated myCurrent to be on the correct node
            myLocalIndex = index - myIndex;
            return myCurrent.info.charAt(myLocalIndex);
        }
        */
        /*        if (index >= mySize || index < 0)
            throw new IndexOutOfBoundsException("index " + index + " is greater than or equal to total length of LinkStrand " + mySize);

        if (myCurrent == null){
//            if(myFirst == null){
//                return '\0';
//            }
//        myCurrent = myFirst;

//            myCurrent = myFirst;
//            return '\0';

            throw new IndexOutOfBoundsException("myCurrent is null");
//            }
        }


//        System.out.println(this);
        if (index < myIndex) {
            myCurrent = myFirst;
            myIndex = 0;
            while (myIndex < index) {
//                System.out.println("index is: " + index);
//                System.out.println("myCurrent.info is: " + myCurrent.info + " myCurrent.info.length() is: " + myCurrent.info.length());
//                System.out.println();
                if (myIndex + myCurrent.info.length() > index) {
                    break;
                } else {
                    myIndex += myCurrent.info.length();
                    myCurrent = myCurrent.next;
                }
            }
            myLocalIndex = index - myIndex;
            myIndex = index;
//            System.out.println(myCurrent.info.charAt(myLocalIndex));
            return myCurrent.info.charAt(myLocalIndex);
        } else if (index == myIndex + 1) {
//            System.out.println("index == myIndex+1");
//            System.out.println("index is: " + index);
//            System.out.println("myLocalIndex is: " + myLocalIndex);
//            System.out.println("myCurrent.info.length() is: " + myCurrent.info.length());
//            System.out.println();

            if (myLocalIndex + 1 == myCurrent.info.length()) {
                myCurrent = myCurrent.next;
                myLocalIndex = 0;
            } else {
                myLocalIndex++;
            }

            myIndex = index;
//            System.out.println(myCurrent.info.charAt(myLocalIndex));
            return myCurrent.info.charAt(myLocalIndex);
        } else {
//            System.out.println("index is: " + index);
//            System.out.println("myLocalIndex is: " + myLocalIndex);
//            System.out.println("myCurrent.info.length() is: " + myCurrent.info.length());
//            System.out.println();



            while (myIndex < index) {
                // System.out.println("myCurrent.info is: " + myCurrent.info + " myCurrent.info.length() is: " + myCurrent.info.length());
                if (myIndex + myCurrent.info.length() > index) {
                    break;
                } else {
                    myIndex += myCurrent.info.length();
                    myCurrent = myCurrent.next;
                }
            }
            myLocalIndex = index - myIndex;
            myIndex = index;
//            System.out.println(myCurrent.info.charAt(myLocalIndex));
            return myCurrent.info.charAt(myLocalIndex);
        }

*/
    }

    @Override
    public String toString() {
        myCurrent = myFirst;
        StringBuilder sb = new StringBuilder();
        while (myCurrent != null) {
            sb.append(myCurrent.info);
            myCurrent = myCurrent.next;
        }
        return sb.toString();
    }
}
