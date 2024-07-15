/**
 * Class that defines an immutable object that represent valid maps in the Game Sokoban
 * 
 * @author fc58208 Maria Rocha
 * @author fc57234 Miguel Henriques
 * 
 */
public class SokobanMap {

  //atributes of this class
    private final boolean [][] FREE_POSITIONS;
    private final int [][] OBJECTIVE_POSITIONS;
    private final  int [][] INICIAL_POSITION_BOX;
    private final int [] INICIAL_POSITION_PLAYER;
  

  
  /**
 * Vefiries if the caractheristics given for the Sokoban map are valid
 *
 * @param rows number of rows in the map
 * @param columns number os columns in the map
 * @param occupiableMap defines which positions on the map are occupiable (meaning they dont have walls)
 * @param goals defines the objective positions in the map
 * @param boxes defines the boxes positions in the map 
 * @param playerPos indicates the initial position of the player  
 * @return if the paramaters given create a valid Sokoban Map
 * 
 * 
 */
    public static boolean isValidMap(int rows, int columns, boolean[][] occupiableMap, 
    int[][] goals, int[][] boxes, int[] playerPos){
       
      // rows, columns>2  occupiableMap!=null e occupiableMap is a matrix of dimension  rows x columns
       boolean isValidMap = rows > 2 && columns >2 &&  occupiableMap!=null && occupiableMap.length == rows 
       && occupiableMap[0].length == columns;
       // verify if it is matrix 
       for(int i = 0; i < occupiableMap.length; i++ ){
        isValidMap &= occupiableMap[0].length == occupiableMap[i].length;
       }
       // -------------------------------goals----------------------------

       // goals is vector of valid positions: goals!=null and goals.length>0
       isValidMap = goals!=null && goals.length>0 ;
      //  goals is a vector of valid positions
      for(int j=0 ; j <goals.length; j ++ ){
        isValidMap = isValidMap &&  goals[j]!=null &&  goals[j].length == 2 && 0 <= goals[j][0] &&  goals[j][0]<rows &&
        0<=goals[j][1] && goals[j][1]<columns;
        //all the elements of goals and boxes are occupiable positions according to occupiableMap
       isValidMap = isValidMap && occupiableMap[goals[j][0]][goals[j][1]];
       isValidMap = isValidMap && occupiableMap[boxes[j][0]][boxes[j][1]];
     
        // goals doesnt have repeated elements
        for(int k=0; k < goals.length; k++ ){
            if (j !=k){   
           isValidMap = isValidMap && goals[j][0] != goals[k][0] || goals[j][1] != goals[k][1];
            }
          }
      }
       
      //----------------------------boxes----------------------------------------

      // boxes is a vector of valid positions
      isValidMap = isValidMap && boxes!=null && boxes.length>0;
      //  boxes is a vector of valid positions
      for(int l=0 ; l < boxes.length; l ++ ){
        isValidMap = isValidMap && boxes[l]!=null &&  boxes[l].length == 2 && 0<= boxes[l][0] &&  boxes[l][0]<rows &&
        0<=boxes[l][1] && boxes[l][1]<columns;
         //playerPos is not contained in the vector boxes 
       isValidMap = isValidMap && (boxes[l][0]!= playerPos[0] || boxes[l][1]!= playerPos[1]);
        // boxes doesnt have repeated elements
        for(int m=0; m < boxes.length; m++ ){
            if (l !=m){
           isValidMap = isValidMap && (boxes[l][0] != boxes[m][0] || boxes[l][1] != boxes[m][1]);
           
            }
          }
      }
         
      //tests if the position given by playerPos represents an occupiable position in occupiableMap
      isValidMap = isValidMap && playerPos[0] >= 0 && playerPos[0] < occupiableMap.length && playerPos[1] >= 0 && playerPos[1] < occupiableMap[0].length&&
      occupiableMap[ playerPos[0]][ playerPos[1]];
     



        //goals.length == boxes.length  

        return isValidMap && goals.length == boxes.length ;




}



  /**
 * Constructs a SokobanMap with the given parameters 
 * 
 * @param rows number of rows in the map
 * @param columns number os columns in the map
 * @param occupiableMap defines which positions on the map are occupiable (meaning they dont have walls)
 * @param goals defines the objective positions in the map
 * @param boxes defines the boxes  positions in the map 
 * @param playerPos indicates the inicial position of the player  
 * @requires {@code isValidMap(rows,columns, occupiableMap,goals,boxes,playerPos) == true}
 * @ensures {@code INICIAL_POSITION_BOX[0].length == 2 && this.INICIAL_POSITION_PLAYER.length == 2}
 */

  //constructor
    public SokobanMap(int rows, int columns, boolean[][] occupiableMap, int[][] goals,
    // creates atributes with the sizes given by the player
    int[][] boxes, int[] playerPos){
      this.FREE_POSITIONS = new boolean[rows][columns];
      this.OBJECTIVE_POSITIONS = new int [goals.length][2];
      this.INICIAL_POSITION_BOX= new int [boxes.length][2];
      this.INICIAL_POSITION_PLAYER = new int [2];
    // creates the atribute - FREE_POSITIONS
      for(int i=0;i<this.FREE_POSITIONS.length;i++){
        for(int j=0;j<this.FREE_POSITIONS[0].length;j++){
         this.FREE_POSITIONS[i][j] = occupiableMap[i][j];
        }
      }
      // creates the atribute - OBJECTIVE_POSITIONS
        for(int k=0; k<OBJECTIVE_POSITIONS.length;k++){
          for(int l=0;l<OBJECTIVE_POSITIONS[0].length;l++){
          this.OBJECTIVE_POSITIONS[k][l]=goals[k][l];
          this.INICIAL_POSITION_BOX[k][l]=boxes[k][l];
          }
        }
         // creates the atribute - INICIAL_POSITION_PLAYER
         this.INICIAL_POSITION_PLAYER[0] =playerPos[0]; 
         this.INICIAL_POSITION_PLAYER[1] =playerPos[1]; 
      
    }

 /**
 * Indicates the number of rows 
 * 
 * @return number of rows 
 */

    public int getRows(){
       int rows = this.FREE_POSITIONS.length;
      return rows;
    }


  /**
  * Indicates the number of columns
  *
  * @return number of columns 
  */
  
    public int getColumns(){
      int columns = this.FREE_POSITIONS[0].length;
      return columns;
    }

  /**
  * Indicates the number of boxes
  *
  * @return number of boxes
  */
  
    public int getNrBoxes(){
      int boxes = this.INICIAL_POSITION_BOX.length;
      return boxes;
    }
  
  /**
  * Indicates the initial position of the player in the SokobanMap
  *
  * @ensures {@code playerPos.length == 2}
  * @return initial position of the player 
  */
    public int[] getInitialPlayerPosition(){

     int[] playerPos = new int [2];
     playerPos[0]= this.INICIAL_POSITION_PLAYER[0];
     playerPos[1]= this.INICIAL_POSITION_PLAYER[1];
     return playerPos;
    }
  /**
  * Indicates the initial position of the player in the SokobanMap
  *
  * @ensures {@code positionBoxes[0].length == 2}
  * @return initial position of the player 
  */
    public int[][] getInitialPositionBoxes(){
      int [][] positionBoxes= new int[this.INICIAL_POSITION_BOX.length][2];
      for(int i=0;i<this.INICIAL_POSITION_BOX.length;i++){
        for(int j=0;j<2;j++){
         positionBoxes[i][j]= this.INICIAL_POSITION_BOX[i][j];
        }
      }
      return positionBoxes;
    }
  /**
  * Indicates the initial positions of the objectives
  *
  * @ensures {@code int [][] positionGoals[0].length == 2}
  * @return initial position of the objectives
  */
    public int[][]getInitialPositionGoals(){
      int [][] positionGoals= new int[this.OBJECTIVE_POSITIONS.length][2];
      for(int i=0;i<this.OBJECTIVE_POSITIONS.length;i++){
        for(int j=0;j<this.OBJECTIVE_POSITIONS[0].length;j++){
         positionGoals[i][j]= this.OBJECTIVE_POSITIONS[i][j];
        }
      }
      return positionGoals;
    }
 /**
  * Checks if the position indicated by the parameters is occupiable 
  *
  * @param row row of the position to check in SokobanMap
  * @param column columns of the position to check in SokobanMap
  * @return the position is occupiable 
  */

    public boolean isOccupiable(int row, int column){
      
      boolean occupiable = this.FREE_POSITIONS[row][column];
      
      return occupiable;
    }
  
  }