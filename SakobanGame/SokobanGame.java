/**
 * This class SokobanGame defines objects that represent matches of Sokoban game
 * 
 * @author fc58208 Maria Rocha
 * @author fc57234 Miguel Henriques
 */
import java.util.Arrays;

public class SokobanGame {

    private SokobanMap map; 
    private int[] playerPos;
    private int[][] boxes;
    private int[][] goals;
    private int moves = 0;
    private int level = 1;
    private static final int[] MOVE_U = {-1,0};
    private static final int[] MOVE_D = {1,0};
    private static final int[] MOVE_L = {0,-1};
    private static final int[] MOVE_R = {0,1};
    private static Direction direction = Direction.DOWN;
    private boolean[][] occupiableMap;
    private int rows;
    private int columns;

    /**
     * Creates a new match with the mapa and the initial positions of goals
     * boxes and players at level 1
     */
    public SokobanGame(){
        occupiableMap = SokobanMapGenerator.getOccupiableMap(level);
        rows = SokobanMapGenerator.getNrRows(level);
        columns = SokobanMapGenerator.getNrColumns(level); 
        playerPos = SokobanMapGenerator.getPlayer(level);
        boxes = SokobanMapGenerator.getBoxes(level);
        goals = SokobanMapGenerator.getGoals(level);
        map = new SokobanMap(rows, columns, occupiableMap, goals, boxes, playerPos);
    }

    /**
     * Returns the number of rows of the map
     * 
     * @return the number of rows
     */
    public int getRows(){
        return map.getRows();
    }

    /**
     * Returns the number of columns of the map
     * 
     * @return the number of columns
     */
    public int getColumns(){
        return map.getColumns();
    }

    /**
     * Returns the current player position
     * 
     * @return the current player position
     */
    public int[] getPlayerPosition(){
        return playerPos;
    }

    /**
     * Returns the direction of the last move
     * 
     * @return the direction of the last move
     */
    public Direction getDirection(){
        return direction;
    }

    /**
     * Returns the current game level
     * 
     * @return the current game level
     */
    public int getLevel(){
        return level;
    }

    /**
     * Returns the number of valid moves done by the player in the current level
     * 
     * @return the number of valid moves
     */
    public int getNrMoves(){
        return moves;
    }

    /**
     * Returns a matrix with the current postition of each box
     * 
     * @return the current position of each box
     */
    public int[][] getPositionBoxes(){
        return boxes;
    }
    
    /**
     * Returns a matrix with the current postition of each goal
     * 
     * @return the current position of each goal
     */
    public int[][] getPositionGoals(){
        return goals;
    }

    /**
     * Checks if the given position is occupiable 
     * 
     * @param i row of the position
     * @param j column of the position
     * @return the position is occupiable
     * @requires {@code 0 <= i && i < getRows() && 0 <= j && j < getColumns()}
     */
    public boolean isOccupiable(int i,int j){
        return map.isOccupiable(i,j);
    }

    /**
     * Executes the given move
     * 
     * @param dir the direction of the move
     * @requires {@code dir=!null && !levelCompleted()}
     */
    public void move(Direction dir){
        
        direction = dir;

        int[] movement = MOVE_D;
        switch (direction) {
            case UP:
                movement = MOVE_U;
                break;
            case DOWN:
                movement = MOVE_D;
                break;
            case LEFT:
                movement = MOVE_L;
                break;
            case RIGHT:
                movement = MOVE_R;
                break;
        }
        int[] nextPos = {playerPos[0] + movement[0], playerPos[1] + movement[1]};
        int[] nextNextPos = {playerPos[0] + movement[0] * 2 , playerPos[1] + movement[1] * 2};
        boolean checkRows = nextPos[0] >= 0 && nextPos[0] <= getRows();
        boolean checkColumns = nextPos[1] >= 0 && nextPos[1] <= getColumns();
        boolean BoxesInRows = nextNextPos[0] >= 0 && nextNextPos[0] <= getRows();
        boolean BoxesInColumns = nextNextPos[1] >= 0 && nextNextPos[1] <= getColumns();
        boolean checkBox = false;
        boolean boxNextToBox = false;
        if (checkRows && checkColumns){
            if (isOccupiable(nextPos[0],nextPos[1])){
                for(int i = 0; i < boxes.length; i++){
                    if(i < boxes.length){
                        if(Arrays.equals(boxes[i],nextPos)){
                            checkBox = true;
                        }
                        if(BoxesInRows && BoxesInColumns && checkBox){
                            if (isOccupiable(nextNextPos[0],nextNextPos[1])){
                                for(int j = 0; j < boxes.length; j++){
                                    if(Arrays.equals(boxes[j],nextNextPos)){
                                        boxNextToBox = true;
                                    }
                                    if(!boxNextToBox && j == boxes.length - 1){
                                        playerPos[0] += movement[0];
                                        playerPos[1] += movement[1];
                                        nextPos[0] += movement[0];
                                        nextPos[1] += movement[1];
                                        boxes[i][0] += movement[0];
                                        boxes[i][1] += movement[1];
                                        i = boxes.length;
                                        j = boxes.length;
                                        moves++;
                                    }
                                }
                            }
                        }
                        
                    }
                    if(!checkBox && i == boxes.length - 1){
                        playerPos[0] += movement[0];
                        playerPos[1] += movement[1]; 
                        i = boxes.length;
                        moves++;
                    }
                }
            }
        }      
    }

    /**
     * Indicates if the current level is completed that is if all boxes are on 
     * goal positions existing only one box per goal position
     * 
     * @return if the current level is completed or not
     * 
     */
    public boolean levelCompleted(){
        boolean boxesInGoals = false;
        boolean notSame = false;
        boolean completed = false;
        int nrBoxes = 0;
        for(int i = 0; i < boxes.length; i++){
            for(int j = 0; j < goals.length; j++){
                if (Arrays.equals(boxes[i], goals[j])){
                    nrBoxes++;
                    if (nrBoxes == goals.length){
                        boxesInGoals = true;
                    }
                }
            }
        }
        for(int i = 0; i < boxes.length - 1; i++){
            for(int j = i+1; j < boxes.length; j++){
                if(boxes[i][0] == boxes[j][0] && boxes[i][1] == boxes[j][1]){
                    notSame = true;
                }
            }
        }
        completed = !notSame && boxesInGoals;
        return completed;
    }

    /**
     * Indicates if the current level is terminated and is the last level
     * 
     * @return if the current level is terminated and is the last level or not
     */
    public boolean isTerminated(){
        int nrOfLevels = SokobanMapGenerator.numberOfLevels();
        boolean end = false;
        if (levelCompleted() && nrOfLevels - getLevel() == 0){
            end = true;
        }
        return end;
    }

    /**
     * Loads the map the initial positions of goals boxes and player in the next level
     * 
     * @requires {@code !isTerminated() && levelCompleted()}
     */
    public void loadNextLevel(){
        level++;
        occupiableMap = SokobanMapGenerator.getOccupiableMap(level);
        rows = SokobanMapGenerator.getNrRows(level);
        columns = SokobanMapGenerator.getNrColumns(level); 
        playerPos = SokobanMapGenerator.getPlayer(level);
        boxes = SokobanMapGenerator.getBoxes(level);
        goals = SokobanMapGenerator.getGoals(level);
        map = new SokobanMap(rows, columns, occupiableMap, goals, boxes, playerPos);
        moves = 0;
        direction = Direction.DOWN;
    }

    /**
     * Resets the current match to its initial state
     * 
     */
    public void restartLevel(){
        direction = Direction.DOWN;
        moves = 0;
        playerPos = SokobanMapGenerator.getPlayer(level);
        boxes = SokobanMapGenerator.getBoxes(level);
        map = new SokobanMap(rows, columns, occupiableMap, goals, boxes, playerPos);
        
    }

    /**
     * Returns a representation of the current state of the match
     * 
     */
    public String toString(){
        String resultingMap = "";
        boolean checkPrints = false;
        for(int i = 0; i < getRows() * 2 + 3; i++){
            if(i == 0 || i == getRows() * 2 + 2){
                resultingMap += "+";
            }
            else{
                resultingMap += "-";
            } 
        }
        resultingMap += "\n LEVEL: " + getLevel() + "\n";

        for(int i = 0; i < getRows() * 2 + 3; i++){
            if(i == 0 || i == getRows() * 2 + 2){
                resultingMap += "+";
            }
            else if(i == getRows() - 1){
                resultingMap += " MAP ";
            }
            else if(i >= getRows() + 4 && i < getRows() * 3 - 2){
                resultingMap += "-"; 
            }
            else if(i < getRows() - 1){
                resultingMap += "-"; 
            }
        }
        resultingMap += "\n";

        for(int i = 0; i < getRows() ; i++){
            resultingMap += "| ";
            for(int j = 0; j < getRows() ; j++){
                checkPrints = false;
                if(!isOccupiable(i, j)){
                    resultingMap += "- " ;
                }
                else{
                    if(getPlayerPosition()[0] == i && getPlayerPosition()[1] == j){
                        resultingMap += "P ";
                        checkPrints = true;
                    }
                    if(!checkPrints){
                        for(int k = 0; k < getPositionGoals().length ; k++){
                            if(getPositionGoals()[k][0] == i && getPositionGoals()[k][1] == j){
                                for(int l = 0; l < getPositionGoals().length ; l++){
                                    if(Arrays.equals(getPositionGoals()[k], getPositionBoxes()[l])){
                                        resultingMap += "* ";
                                        checkPrints = true;
                                    }
                                    if(l == getPositionGoals().length - 1 && !checkPrints){
                                        resultingMap += "G ";
                                        checkPrints = true;
                                    }
                                }
                            }
                        }
                    }
                    if(!checkPrints){
                        for(int k = 0; k < getPositionBoxes().length ; k++){
                            if(getPositionBoxes()[k][0] == i && getPositionBoxes()[k][1] == j){
                                resultingMap += "B ";
                                checkPrints = true;
                            }
                        }
                    }
                    if(!checkPrints){
                        resultingMap += "  ";
                        checkPrints = true;
                    }
                }
            }
            resultingMap += "| \n";
        }
        for(int i = 0; i < getRows() * 2 + 3; i++){
            if(i == 0 || i == getRows() * 2 + 2){
                resultingMap += "+";
            }
            else{
                resultingMap += "-";
            } 
        }
        resultingMap += "\n MOVES: " + getNrMoves() + "\n";

        for(int i = 0; i < getRows() * 2 + 3; i++){
            if(i == 0 || i == getRows() * 2 + 2){
                resultingMap += "+";
            }
            else{
                resultingMap += "-";
            } 
        }
        return resultingMap;
    }
}   