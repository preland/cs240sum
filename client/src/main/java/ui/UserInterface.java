package ui;

public class UserInterface {
    boolean quit = false;
    boolean postLogin = false;
    public void run() {
        String rawInput = "";
        System.out.println("welcome chess. type help for help. type quit for unhelp");
        while (!quit) {
            rawInput = System.in.toString();
            String[] input = rawInput.split(" ");
            if (postLogin) {
                switch (input[0]) {
                    case "help":
                        System.out.println("lol no help for you, sucks ig");
                        break;
                    case "logout":
                        handleLogout();
                        break;
                    case "create":
                        handleCreateGame();
                        break;
                    case "list":
                        handleListGames();
                        break;
                    case "play":
                        handlePlayGame();
                        break;
                    case "view":
                        handleViewGame();
                        break;
                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            } else {
                switch (input[0]) {
                    case "help":
                        System.out.println("lol no help for you, sucks ig");
                        break;
                    case "quit":
                        quit = true;
                        System.out.println("goodb");
                        break;
                    case "login":
                        if(input.length != 3) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        handleLogin(input[1], input[2]);
                        break;
                    case "register":
                        if(input.length != 4) {
                            System.out.println("invalid input. try again, or type help");
                            break;
                        }
                        handleRegister(input[1], input[2], input[3]);
                        break;
                    default:
                        System.out.println("invalid input. try again, or type help");
                }
            }
        }
    }
}
