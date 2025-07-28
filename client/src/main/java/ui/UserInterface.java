package ui;

public class UserInterface {
    boolean quit = false;
    boolean postLogin = false;
    public void run() {
        String input = "";
        System.out.println("welcome chess. type help for help. type quit for unhelp");
        while (!quit) {
            input = System.in.toString();
            if (postLogin) {
                switch (input) {
                    case "help":
                        System.out.println("lol no help for you, sucks ig");
                        break;

                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            } else {
                switch (input) {
                    case "help":
                        System.out.println("lol no help for you, sucks ig");
                        break;
                    case "quit":
                        quit = true;
                        System.out.println("goodb");
                        break;
                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            }
        }
    }
}
