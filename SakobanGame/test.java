public class test{

    public static void main(String [] args){
        boolean [] [] occupiableMap = {{true,false,true},{true,false,false},{true,true,false}};
        int [][] goals = {{0,0},{1,0}};
        int [] [] boxes ={{0,2},{2,1}};
        int [] playerPos = {2,0};
       
     
   SokobanMap map = new SokobanMap(3,3,occupiableMap, goals, boxes,playerPos);
   int [] [] pos = new int [2][2];
   pos = map.getInitialPositionGoals();
   System.out.println(pos[0][0]+" "+pos[0][1]);
        
        
    }

    
}