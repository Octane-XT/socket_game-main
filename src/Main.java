import map.Field;

public class Main {
    public static void main(String[] args) {

        Field field = new Field(20, 20);
        field.fill_map();
        field.generate_wall();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(field.getMap()[i][j]);
            }
            System.out.println();
        }
    }
}