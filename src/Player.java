import java.util.*;

public class Player {
    static Random random = new Random();

    private static int pos;
    private static int arrows = 5;

    public static int getPos() {return pos;}
    public static void setPos(int pos) {Player.pos = pos;}
    public static int getArrow() {return arrows;}
    
    // Spawn method for player
    public static Map<Integer, Room> spawnPlayer(Map<Integer, Room> rooms) {
        boolean emptyRoom = false;
        while (emptyRoom == false) {
            Player.pos = random.nextInt(20) + 1;
            if (rooms.get(Player.pos).isEmpty()) {emptyRoom = true;}
        }
        return rooms;
    }

    // Method to move the player to a room specified by the user
    public static void movePlayer(int choice) {Player.pos = choice;}

    // Verification method to check that the room specified by the user is indeed one of the player's current adjacent rooms
    public static boolean verifyMovement(Room room, int enteredRoom) {
        return ((room.getRoomA() == enteredRoom) || (room.getRoomB()  == enteredRoom) || (room.getRoomC() == enteredRoom));}
    
    public static boolean shootSomething(Map<Integer, Room> rooms, Bat[] bats, Room room) {

        if (room.getWumpusR()) { // If the Wumpus is in the selected room, it is game over immediately
            System.out.println("With just one arrow drawn, slay you a beast that has bested innumerous great hunters. You win!"); 
            return true;
        }

        // Check if room has wumpusPresence toggled to true, which will decide if the Wumpus moves
        if (rooms.get(Player.pos).getWumpusPresence()) {
            System.out.println("You missed!");
            Wumpus.moveWumpus(rooms);
            if (Wumpus.getWumpusPos() != Player.pos) {
                if (room.getPitR()) {
                    System.out.println("You don't hear the arrow clink against the floor."); 
                    arrows -= 1;
                }

                if (room.getBatR()) {
                    System.out.println("You hear the annoyed shrieks of a bat.");
                    arrows -= 1;
                }

                if (room.isEmpty()) {
                    System.out.println("You hear the arrow clink against the floor."); 
                    arrows -= 1;
                }
                
                return Room.movementStatus(rooms, bats, rooms.get(Player.pos), Player.pos);

            } else {

                return Room.movementStatus(rooms, bats, rooms.get(Player.pos), Player.pos);
            }

        } else {

            if (room.getPitR()) { // For if the player shoots an arrow into the pit's room
                System.out.println("You don't hear the arrow clink against the floor."); 
                arrows -= 1; 
                return false;
            }

            if (room.getBatR()) {
                System.out.println("You hear the annoyed shrieks of a bat.");
                arrows -= 1;
                return false;
            }

            if (room.isEmpty()) { // Normal miss 
                System.out.println("You hear the arrow clink against the floor."); 
                arrows -= 1; 
                return false;
            }
        }

        return false;
    }        
}