/**
 * A DoubleLinkedSeq is a sequence of double numbers. The sequence can have a
 * special &quot;current element&quot;, which is specified and accessed through
 * four methods that are not available in the IntArrayBag class (start,
 * getCurrent, advance, and isCurrent).
 * 
 * Limitations:
 * 
 * Beyond Integer.MAX_VALUE element, the size method does not work.
 * 
 * @author Jenna Schachner
 * @version 1
 */
public class DoubleLinkedSeq implements Cloneable
{
    private DoubleNode head;
    private DoubleNode tail;
    private DoubleNode cursor;
    private DoubleNode precursor;
    private int manyNodes;
    /**
     * Initializes an empty DoubleLinkedSeq.
     * 
     * @postcondition This sequence is empty.
     */
    public DoubleLinkedSeq()
    {
        head = null;
        tail = null;
        cursor = null;
        precursor = null;
        manyNodes = 0;
    }

    /**
     * Adds a new element to this sequence.
     * 
     * @param element
     *            the new element that is being added to this sequence.
     * 
     * @postcondition a new copy of the element has been added to this sequence.
     *                If there was a current element, then this method places
     *                the new element before the current element. If there was
     *                no current element, then this method places the new
     *                element at the front of this sequence. The newly added
     *                element becomes the new current element of this sequence.
     */
    public void addBefore(double element)
    {           
            if (manyNodes == 0)
            {
                head = new DoubleNode(element, null);
                tail = head;
                cursor = head;
            }
            else if (cursor == head)
            {
                head = new DoubleNode(element, head);
                cursor = head;
            }
            else if (cursor == null) {
                head = new DoubleNode(element, head);
                cursor = head;
            }
            else
            {
                precursor.addNodeAfter(element);
                cursor = precursor.getLink();
            }
       manyNodes++; 
    }

    /**
     * Adds a new element to this sequence.
     * 
     * @param element
     *            the new element that is being added to this sequence.
     * 
     * @postcondition a new copy of the element has been added to this sequence.
     *                If there was a current element, then this method places
     *                the new element after the current element. If there was no
     *                current element, then this method places the new element
     *                at the end of this sequence. The newly added element
     *                becomes the new current element of this sequence.
     */
    public void addAfter(double element)
    {
        if (manyNodes == 0)
        {
            head = new DoubleNode(element, null);
            tail = head;
            cursor  = head;
        }
        else if (cursor == null || cursor == tail)
        {
            precursor = tail;
            tail = new DoubleNode(element, null);
            precursor.setLink(tail);
            cursor = tail;
        }
        else 
        {
            cursor.addNodeAfter(element);
            precursor = cursor;
            cursor = cursor.getLink();
        }
        manyNodes++;
    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     * 
     * @precondition other must not be null.
     * 
     * @param other
     *            a sequence show contents will be placed at the end of this
     *            sequence.
     * 
     * @postcondition the elements from other have been placed at the end of
     *                this sequence. The current element of this sequence
     *                remains where it was, and other is unchanged.
     * 
     * @throws NullPointerException
     *             if other is null.
     */
    public void addAll(DoubleLinkedSeq other) throws NullPointerException
    {
        DoubleNode[] copy;
        if (other == null)
        {
            throw new NullPointerException
                ("new sequence is null");
        }
        else if (other.size() > 0)
        {
            copy = DoubleNode.listCopyWithTail(other.head);
            tail.setLink(copy[0]); // set tail of current sequence to end of other
            copy[1].setLink(null); // set the link of the last item to null
            tail = copy[1];
            manyNodes += other.size();
        }
    }

    /**
     * Move forward so that the current element is now the next element in the
     * sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @postcondition If the current element was already the end element of this
     *                sequence (with nothing after it), then there is no longer
     *                any current element. Otherwise, the new element is the
     *                element immediately after the original current element.
     * 
     * @throws IllegalStateException
     *             if there is not current element.
     */
    public void advance() throws IllegalStateException
    {
        if (!isCurrent())
        {
            throw new  IllegalStateException
                ("There is no current element.");
        }
        else if (cursor.getLink() == null) {
            precursor = null;
            cursor = null;
        }
        else
        {
			precursor = cursor;
			cursor = cursor.getLink(); // moves cursor to next link
        }
    }

    /**
     * Creates a copy of this sequence.
     * 
     * @return a copy of this sequence. Subsequent changes to the copy will not
     *         affect the original, nor vice versa.
     * @throws RuntimeException
     *             if this class does not implement Cloneable.
     * 
     */
    public DoubleLinkedSeq clone() throws RuntimeException
    {
        DoubleLinkedSeq clone;
        try
        {
            clone = (DoubleLinkedSeq)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException
                ("This class doesn't implement Cloneable.");
        }
        clone.head = DoubleNode.listCopy(head);
        return clone;
    }

    /**
     * Creates a new sequence that contains all the elements from s1 followed by
     * all of the elements from s2.
     * 
     * @precondition neither s1 nor s2 are null.
     * 
     * @param s1
     *            the first of two sequences.
     * @param s2
     *            the second of two sequences.
     * 
     * @return a new sequence that has the elements of s1 followed by the
     *         elements of s2 (with no current element).
     * 
     * @throws NullPointerException
     *             if s1 or s2 are null.
     */
    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1,
            DoubleLinkedSeq s2) throws NullPointerException
    {
        if ((s1 == null) || ( s2 == null))
        {
            throw new NullPointerException
                ("s1 or s2 is null.");
        }
        DoubleLinkedSeq concat = new DoubleLinkedSeq();
        concat.addAll(s1);
        concat.addAll(s2);
        return concat;
    }

    /**
     * Returns a copy of the current element in this sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @return the current element of this sequence.
     * 
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public double getCurrent() throws IllegalStateException
    {
        return cursor.getData();
    }

    /**
     * Determines whether this sequence has specified a current element.
     * 
     * @return true if there is a current element, or false otherwise.
     */
    public boolean isCurrent()
    {
        boolean b = false;
        if (cursor == null)
        {
            b = false;
        }
        else
        {
            b = true;
        }
        return b;
    }

    /**
     * Removes the current element from this sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @postcondition The current element has been removed from this sequence,
     *                and the following element (if there is one) is now the new
     *                current element. If there was no following element, then
     *                there is now no current element.
     * 
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public void removeCurrent() throws IllegalStateException
    {
        if (!isCurrent())
        {
            throw new IllegalStateException
                ("There is no current element.");
        }
        else if (manyNodes == 1)
        {
            head = null;
            cursor = null;
            precursor = null;
            tail = null;
            manyNodes--;
        }
        else if (manyNodes > 1)
        {
             if (cursor == head)
             {
                 head = cursor.getLink();
                 cursor = head;
                 manyNodes--;
                 precursor = null;
             }
             else if (cursor == tail)
             {
                 tail = precursor;
                 cursor = null;
                 precursor = null;
                 tail.setLink(null);
                 manyNodes--;
             }
             else 
             {
                 cursor = cursor.getLink();
                 precursor.removeNodeAfter();
                 precursor.setLink(cursor);
                 manyNodes--;
             }
        }
    }

    /**
     * Determines the number of elements in this sequence.
     * 
     * @return the number of elements in this sequence.
     */
    public int size()
    {
        return manyNodes;
    }

    /**
     * Sets the current element at the front of this sequence.
     * 
     * @postcondition If this sequence is not empty, the front element of this
     *                sequence is now the current element; otherwise, there is
     *                no current element.
     */
    public void start()
    {
		// TODO:
        if (manyNodes != 0) 
		{
		    precursor = null;
			cursor = head;
		}
		else {
			cursor = null;
			precursor = null;
		}
    }

    /**
     * Returns a String representation of this sequence. If the sequence is
     * empty, the method should return &quot;&lt;&gt;&quot;. If the sequence has
     * one item, say 1.1, and that item is not the current item, the method
     * should return &quot;&lt;1.1&gt;&quot;. If the sequence has more than one
     * item, they should be separated by commas, for example: &quot;&lt;1.1,
     * 2.2, 3.3&gt;&quot;. If there exists a current item, then that item should
     * be surrounded by square braces. For example, if the second item is the
     * current item, the method should return: &quot;&lt;1.1, [2.2],
     * 3.3&gt;&quot;.
     * 
     * @return a String representation of this sequence.
     */
    @Override
    public String toString()
    {
        String s = "";
        if (manyNodes == 0)
        {
            s = "<>";
        }
        else if (manyNodes == 1)
        {
            if (cursor == null)
            {
                s = "<" + head.getData() + ">";
            }
            else
            {
                s = "<[" + head.getData() + "]>";
            }
        }
        else if (manyNodes > 1)
        {
            s = "<";
            DoubleNode counter = head;
            for (int i = 0; i < manyNodes; i++)
            {
                // if there is no current element
                if (cursor == null) {
                    s += counter.getData();
                }
                // else evaluate whether counter is current element
                else {
                    if (cursor.equals(counter)) {
                        // mark cursor
                        s += "[" + counter.getData() + "]";
                    }
                    // mark precursor for testing purposes
                    else if (precursor != null && precursor.equals(counter))
                    {

                        s += "(" + counter.getData() + ")";
                    }
                    // mark tail for testing purposes
                    else if (tail.equals(counter)) {
                        s += "{" + counter.getData() + "}";
                    }
                    else
                    {
                        s += counter.getData();
                    }
                }
                // advance counter if not at end of list
                if (counter.getLink() != null)
                {
                    s += ", ";
                    counter = counter.getLink();
                }
            }
            s += ">";
        }

            return s;
    }

    /**
     * Determines if this object is equal to the other object.
     * 
     * @param other
     *            The other object (possibly a DoubleLinkedSequence).
     * @return true if this object is equal to the other object, false
     *         otherwise. Two sequences are equal if they have the same number
     *         of elements, and each corresponding element is equal
     */
    public boolean equals(Object other)
    {
        Boolean b = other.toString().equals(toString());
        return b;
    }
}
