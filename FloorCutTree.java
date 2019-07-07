//Mert KAYA 5 July 2019

/**
 * FloorCutTree can be used to find minimum rectangle containing different rectangular shapes with 
 *  various widths and heights.
 */
public class FloorCutTree<E> extends LinkedBinaryTree<E> {
 
    /**
     * Empty constructor
     */
    public FloorCutTree(){
        super();
    }


    /**
     * Returns true if word consists only of letters
     * @param word 
     * @return if it only contains letters
     */
    public boolean isOnlyLetter(String word){
        return word.matches("[a-zA-Z]");
    }

    /**
     * Creates root labeled v
     * @param v is the label of newly created node which will become root
     * @throws IllegalArgumentException if v contains non letter
     */
    public void create_root(String v) throws IllegalArgumentException{
        
        if(!isOnlyLetter(v)){
            throw new IllegalArgumentException("ERROR! Contains non letters");
        }

        this.addRoot((E)v);

    
    }

    /**
     * Returns the position that has str as label
     * @param str Label of target node
     * @return the target node.
     */
    public Position<E> getPos(String str){

        for(Position p : this.positions() ){

            if(validate(p).getLabel().equals(str)){
                return p;
            }

        }
        return null;
                
    }

    /**
     * Cuts the node creates 2 childs labeled lv and rv
     * Changes the label of node that labeled v to '|' if cut == 'v'
     * Changes the label of node that labeled v to '-' if cut == 'h'
     * 
     * @param cut Type of the cut
     * @param v ParentNode's Label
     * @param lv    LeftNode's Label
     * @param rv    RightNode's Label
     * @throws IllegalArgumentException if String are not in tree or internal or contain non letter
     */
    private void cut(char cut,String v, String lv, String rv) throws IllegalArgumentException{

        Position parentPos = getPos(v);
        
        if(parentPos == null){
            throw new IllegalArgumentException("ERROR! " + v +" is not in Tree"); 
        }

        if(this.isInternal(parentPos)){
            throw new IllegalArgumentException("ERROR! Internal Position!");
        }

        if(isOnlyLetter(lv)){
            this.addLeft(parentPos, (E)lv);
        }else { throw new IllegalArgumentException("ERROR! Contains non letters");}

        if(isOnlyLetter(rv)){
            this.addRight(parentPos, (E)rv);
        }else{  throw new IllegalArgumentException("ERROR! Contains non letters");}

        Node<E> parentNode = validate(parentPos);
        parentNode.setCut(cut);
        parentNode.setHeight(0);
        parentNode.setWidth(0);


    }


    /**
     * Calls cut method with 'h' given as a symbol of horizontal
     * @param v ParentNode's Label
     * @param lv    LeftNode's Label
     * @param rv    RightNode's Label
     * @throws IllegalArgumentException if String are not in tree or internal or contain non letter
     */
    public void cut_horizontal(String v, String lv, String rv) throws IllegalArgumentException{

        cut('h',v,lv,rv);
        
    }

    
    /**
     * Calls cut method with 'h' given as a symbol of vertical
     * @param v ParentNode's Label
     * @param lv    LeftNode's Label
     * @param rv    RightNode's Label
     * @throws IllegalArgumentException if String are not in tree or internal or contain non letter
     */
    public void cut_vertical(String v, String lv, String rv) throws IllegalArgumentException{
    
        cut('v',v,lv,rv);
    
    }

    /**
     * Sets width of the Node that has v as a label
     * 
     * @param v ParentNode's Label
     * @param width Value of width
     * @throws IllegalArgumentException if String are not in tree or internal
     */
    public void assign_width(String v , int width) throws IllegalArgumentException{
        Position parentPos = getPos(v);
        if(parentPos == null){
            throw new IllegalArgumentException("ERROR! " + v +" is not in Tree"); 
        }

        if(this.isInternal(parentPos)){
            throw new IllegalArgumentException("ERROR! Internal Position!");
        }
        Node<E> parentNode = validate(parentPos);

        if(parentNode.getCut() == 'n'){
            parentNode.setWidth(width);
        }
    }


    /**
     * 
     * Sets height of the Node that has v as a label
     * @param v ParentNode's label
     * @param height Value of height
     * @throws IllegalArgumentException if String are not in tree or internal
     */
    public void assign_height(String v , int height) throws IllegalArgumentException{
        Position parentPos = getPos(v);
        
        if(parentPos == null){
            throw new IllegalArgumentException("ERROR! " + v +" is not in Tree"); 
        }

        if(this.isInternal(parentPos)){
            throw new IllegalArgumentException("ERROR! Internal Position!");
        }

        Node<E> parentNode = validate(parentPos);

        if(parentNode.getCut() == 'n'){
            parentNode.setHeight(height);
        }

    }


    /**
     * CompactAux traverses the list by postorder
     * Gets height and width from external nodes (Nodes have 'n' as a cut)
     * Uses them to find height and width of internal nodes which
     * will be used to find the height and width of their parents
     * Uses this technique until everynode including root Node has values
     */
    public void compact(){

        Node<String> leftNode = (Node <String>) validate((Position<E>)this.left(this.root));
        Node<String> rightNode = (Node <String>) validate((Position<E>)this.right(this.root));

        if(this != null){

            int[] array = compactAux(leftNode , rightNode);
            this.root.setHeight(array[0]);
            this.root.setWidth(array[1]);
            
        }

        

    }

    /**
     * Traversals in postorder to get childs values first
     * Than uses those in parents values(height and width)
     * 
     * If node is external uses values of it
     * else uses ;
     * width = max of leftwidth and rightwidth if parents cut is horizontal
     * width = leftwidth + rightwidth if parents cut is vertical
     * 
     * height = max of leftwidth and rightwidth if parents cut is vertical
     * height = leftwidth + rightwidth if parents cut is horizontal
     * 
     *  
     * @param leftNode LeftNode of the start
     * @param rightNode RightNode of the start
     * @return an array that contains final height and width for root.
     */
    private int[] compactAux(Node<String> leftNode , Node<String> rightNode ){

        int[] leftCValue =  null;

        //Goes to left if internal(postOrder)
        if(!this.isExternal((Node<E>)leftNode)){

            Node<String> llc = (Node<String>)this.left((Position<E>)leftNode);
            Node<String> lrc = (Node<String>)this.right((Position<E>)leftNode);
            leftCValue = compactAux(llc,lrc);

        }else{

            //Gets Values if external
            int leftHeight = leftNode.getHeight();
            int leftWidth = leftNode.getWidth();
            leftCValue = new int [2];
            leftCValue[0] = leftHeight;
            leftCValue[1] = leftWidth;

        }


        //Proceeding to Right side (PostOrder technique)
        int[] rightCValue;
        if(!this.isExternal((Position<E>)rightNode)){

            Node<String> rlc = (Node<String>)this.left((Position<E>)rightNode);
            Node<String> rrc = (Node<String>)this.right((Position<E>)rightNode);
            rightCValue = compactAux(rlc,rrc);

        }else{

            //Gets Values if external
            int rightHeight = rightNode.getHeight();
            int rightWidth = rightNode.getWidth();

            rightCValue = new int [2];
            rightCValue[0] = rightHeight;
            rightCValue[1] = rightWidth;

        }

        //Proceeds to parent node (PostOrder traversal)
        Position<E> parentPos = ( Position<E> ) this.parent((Position<E>)leftNode);
        Node<E> parentNode = validate(parentPos);
        
        int returnedWidth = 0; //Returned to parent (Goes less depth recursively)

        if(parentNode.getCut() == 'h'){
        
            returnedWidth = Integer.max(leftCValue[1],rightCValue[1]);
        
        }else if(parentNode.getCut() == 'v'){
        
            returnedWidth = leftCValue[1]+rightCValue[1];
        
        }

        int returnedHeight = 0;

        //Horizontal cut
        if(parentNode.getCut() == 'h'){
            returnedHeight = leftCValue[0] + rightCValue[0];

        //Vertical Cut
        }else if(parentNode.getCut() == 'v'){
            returnedHeight = Integer.max(leftCValue[0],rightCValue[0]);
        }

        //Setting parent node's values
        parentNode.setHeight(returnedHeight);
        parentNode.setWidth(returnedWidth);

        //Cumulatively this will become roots
        return new int[] { returnedHeight,returnedWidth };

        



    }

    /**
     * 
     * – Nodes will be printed according to their order in an inorder traversal, one node per line.
     * – The entry for a node will be preceded by depth number of dots, where depth denotes
     *  the depth of the node in the tree.
     * – For an internal node that corresponds to a horizontal cut, with width w and height h,
     * the entry will be printed as (-:(w,h)). ( If the width and/or height has not been
     * assigned to a node yet, display 0 as placeholder, for example, (-:(0,0)). )
     * – For an internal node that corresponds to a vertical cut, with width w and height h, the
     * entry will be printed as(|:(w,h)).
     * – For an external node with width w, height h, and label L, the entry will be printed as (L:(w,h)).
     * 
     */
    public void display(){
        System.out.println("Display : ");
        for(Position<E> pos : this.positions()){

            Node<E> myNode = validate((Position<E>) pos);
            int depthNum = this.depth((Position<E>)myNode);

            if(myNode.getCut() == 'n'){
            
                for(int i = 0 ; i < depthNum ; i++){
                    System.out.print(".");
                }

                System.out.println("("+myNode.getLabel()+":"+"("+myNode.getWidth()+", "+myNode.getHeight()+"))");

            }else if(myNode.getCut() == 'h'){

                for(int i = 0 ; i < depthNum ; i++){
                    System.out.print(".");
                }

                System.out.println("(-:"+"("+myNode.getWidth()+", "+myNode.getHeight()+"))");

            }else if(myNode.getCut() == 'v'){

                for(int i = 0 ; i < depthNum ; i++){
                    System.out.print(".");
                }

                System.out.println("(|:"+"("+myNode.getWidth()+", "+myNode.getHeight()+"))");

            }


        }
        System.out.println("");
    }

}