public class MyLinkedList <AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }
    
    private void clear( )
    {
        doClear( );
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;
        
        theSize = 0;
        modCount++;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );   
        return true;         
    }
    
    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    
    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */    
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
        modCount++;
    }   
    
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        
        p.data = newVal;   
        return oldVal;
    }
    
    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */    
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;
        
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
            
        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;            
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }
    
    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        
        return p.data;
    }
    
    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }
    
    public void swap(int id1, int id2)   
    {
    	AnyType tempValue1;
    	Node<AnyType> p;
    	Node<AnyType> q;
    	int temp;
    	
    	//Ensure id1 is always the smaller index
		if (id1 - id2 > 0)                      //was not swapping same value was given to both.
		{
			temp = id1;
			id1 = id2;
			id2 = temp;
		}
    	//Validates the Indices
    	if(id1 >= 0 && id1 < size() && id2 >= 0 && id2 < size()) 
    	{
    		//id1 lies in the second half
			if (id1>=(size()/2))
			{
				//id1 and id2 are in second half
				//Start iterating from tail
	
				p = endMarker;
	            for( int i = size( ); i > id2; i-- )
	                p = p.prev;
				tempValue1 = p.data;
				
				//Copying position of p into q
				q=p;
				
				//Continue iterating from id2 to id1
				for( int i = id2; i > id1; i-- )  //removed = in i < id1
	                q = q.prev;
				
				p.data = q.data;
				q.data = tempValue1;
				
			}
			else
			{
				//id1 lies in first half
				if ((id2 - id1) < (size() - id2)  )
				{ 
					//Distance between id1 and id2 is lesser than distance between id2  and tail 
					p = beginMarker;
		            for( int i = 0; i <= id1; i++ )
		                p = p.next;    
					tempValue1 = p.data;
					
					//Copying position of p into q
					q=p;
					
					//Continue iterating from id2 to id1
					for( int i = id1; i < id2; i++ )
		                q = q.next;                        //changed prev to next
					
					p.data = q.data;
					q.data = tempValue1;
				}
				else
				{
					//Distance between id2  and tail  is lesser than distance between id1 and id2
					p = beginMarker;                              //removed beginMarker.next
					for( int i = 0; i <= id1; i++ )
						p = p.next;    
			
					tempValue1 = p.data;
			
	
					//new counter from tail
					q = endMarker;
					for( int i = size( ); i > id2; i-- )
						q = q.prev;
			
					//Swapping values
					p.data = q.data;
					q.data = tempValue1;
				}
			}
    	}
		else
		{
			System.out.println("Invalid Indices");
		}
    }		
    

    
    public void reverse()
    {	
    	//Declare the variables
    	Node<AnyType> begin = beginMarker;
    	Node<AnyType> end = endMarker;
    	AnyType temp ;
    	    	
    	//move from begin towards end and move from end towards begin and swap values
    	for (int i = 0 , j = size() ; i<j ; i++, j-- )
    	{
    		temp = begin.next.data;
    		begin.next.data = end.prev.data;
    		end.prev.data = temp;
    		begin = begin.next;
    		end = end.prev;
    	}
    } 
    
    public void erase(int id1, int n)	
    {
    	Node<AnyType> tempValue1;
    	Node<AnyType> p;
    	Node<AnyType> q;
    	int id2= id1+n;
    	
    	
    	
    	//Validates the Indices
    	if(id1 > 0 && id1 < size() && id2 > 0 && id2 < size()&& n >= 0)
    	{
    		
    			//determine relative post of id1
    			if (id1>=(size()/2))
    			{
    				//id1 and id2 are in second half
    				//Start iterating from tail

    				p = endMarker;
    	            for( int i = size( ); i > id2; i-- )
    	                p = p.prev;
    				tempValue1 = p;
    				
    				//Copying position of p into q
    				q=p;
    				
    				//Continue iterating from id2 to id1
    				for( int i = id2; i >= id1; i-- )
    	                q = q.prev;
    				
    				tempValue1.prev =q;
    				q.next=p;
    			}
    			else
    			{
    				//id1 lies in first half
    				if ((id2 - id1) < (size() - id2)  )
    				{ 
    					//Distance between id1 and id2 is lesser than distance between id2  and tail 
    					p = beginMarker;
    		            for( int i = 0; i < id1; i++ )
    		                p = p.next;    
        				tempValue1 = p;
        				
        				//Copying position of p into q
        				q=p;
        				
        				//Continue iterating from id2 to id1
        				for( int i = id1; i <= id2; i++ )
        	                q = q.next;
        				tempValue1.next =q;
        				q.prev=p;
    				}
			    	else
					{
						//Distance between id2  and tail  is lesser than distance between id1 and id2
						p = beginMarker;
			            for( int i = 0; i < id1; i++ )
			                p = p.next;    
						
			            tempValue1 = p;
						
			
						//new counter from tail
						q = endMarker;
						for( int i = size( ); i > id2; i-- )
			                q = q.prev;
						
						//Swapping values
						tempValue1.next =q;
						q.prev=p;
					}
    				
    			}
    				
 
    		}
    	else
    	{
    		System.out.println("Invalid Indices");
    	}
    	
    }
	public void insert(int n, MyLinkedList<AnyType> isrtLst)
    	{
    		Node<AnyType> addListFirst;
    		Node<AnyType> newlistlst;
    		Node<AnyType> temp;
    		Node<AnyType> current;
    		
    		
    		addListFirst = isrtLst.beginMarker.next;
    		newlistlst = isrtLst.endMarker.prev;
    		
    		//Validate the the index 
    		if (n <= size()&& n>=0)
    		{
    			if(n<size()/2)  // Start from head
    			{
    				current=beginMarker;
    				for( int i = 0; i < n; i++ )
    		       {
    		    	   current = current.next;
    		       }
    				temp=current.next;
    				current.next=addListFirst;
    				addListFirst.prev = current;
    				newlistlst.next = temp;
    				temp.prev = newlistlst;
    				
    			}
    			else //Begin from Tail
    			{
    				current=endMarker;
    				for( int i = size(); i > n; i-- )
    				{
    					current = current.prev;	
    				}
    				temp=current.prev;
    				current.prev=newlistlst;
    				newlistlst.next = current;
    				temp.next = addListFirst;
    				addListFirst.prev = temp;    				
    			}
    				
    		}
    		
    		else
    		{
    			System.out.println("Invalid Index");
    		}
    	}
    		


    
    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        
        public boolean hasNext( )
        {
            return current != endMarker;
        }
        
        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( ); 
                   
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        
        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
                
            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;       
        }
        
    }
    
    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    
    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;
}

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        MyLinkedList<Integer> lst = new MyLinkedList<>( );
        MyLinkedList<Integer> isrtLst = new MyLinkedList<>( );

        for( int i = 0; i < 10; i++ )
                lst.add( i );
       // for( int i = 20; i < 30; i++ )
             //   lst.add( 0, i );
        for( int i = 30; i < 35; i++ )
            isrtLst.add( i );
        //Input Array
        System.out.println("Unaltered Input");
        System.out.println(lst);

        //Swap
        System.out.println("Swapping indices 3 and 5 Input");
        lst.swap(3,5);
        System.out.println(lst);
        
        //Reverse
        System.out.println("Reversing the List displsyed above");
        lst.reverse();
        System.out.println(lst);
        
        //Erase
        System.out.println("Erasing the 3 values from index 5");
        lst.erase(5, 3);
        System.out.println(lst);
        
        //Insert
        System.out.println("Inserting a list at index 2");
        lst.insert(2, isrtLst);;
        System.out.println(lst);        
    }

}
