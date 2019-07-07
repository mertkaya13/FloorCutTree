import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Mert KAYA 5 July 2019

/**
 * FloorCutTreeDriver's purpose is to use FloorCutTree with a txt file as an entry
 * File must be in same folder
 * It must have the name  " test-hw3.txt "
 */

public class FloorCutTreeDriver {
    
    //Our floorCutTree
    static FloorCutTree floortree = new FloorCutTree<>(); 


    /**
     * 
     * readDirective reads directives and calls corresponding methods of FloorCutTree
     * 
     * @param directive Directive can contain 0 or more arguments
     * These arguments will be the 1st 2nd 3rd .. elements of directive array
     */

    private static void readDirective(String[] directive){

        if(directive[0].equals("create-root")){

            floortree.create_root(directive[1]);

        }else if(directive[0].equals("cut-h")){
            return
            floortree.cut_horizontal( directive[1] , directive[2] , directive[3] );
        
        }else if(directive[0].equals("cut-v")){
        
            floortree.cut_vertical( directive[1] , directive[2] , directive[3] );
        
        }else if(directive[0].equals("assign-w")){
        
            floortree.assign_width( directive[1] , Integer.parseInt(directive[2]) );
        
        }else if(directive[0].equals("assign-h")){
        
            floortree.assign_height( directive[1] , Integer.parseInt(directive[2]) );
        
        }else if(directive[0].equals("compact")){
            
            floortree.compact();

        }else if(directive[0].equals("display")){
            
            floortree.display();

        }else if(directive[0].equals("quit")){
            
            System.exit(0);

        }

    }


    /**
     * directiveParser takes sentence and splits to an array
     * every argument will have 'tab' between
     * @param sentence raw String that contains directive and arguments
     */
    private static void directiveParser(String sentence){
        
        String[] array = sentence.split("\t");
        readDirective(array);

    }


    /**
     * Reads file named "test-hw3.txt"
     * Sends everyLine to directiveParser
     * EveryLine will have 1 directive and 0 or more arguments
     */
    private static void readFile(){

        Scanner fileScanner;
        try{
            fileScanner = new Scanner(new File("test-hw3.txt"));
            while(fileScanner.hasNextLine()){

                String directiveLine = fileScanner.nextLine();
                directiveParser(directiveLine);

            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    
    }

    public static void main(String[] args){
    
        //Calls read File as a start.
        readFile();
        
        
    
    }




}