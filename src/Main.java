import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        System.out.print("Uninformed (1) OR Informed (2)?: ");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        if (input == 1) new Uninformed();
        else new Informed();
    }
}
