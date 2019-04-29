package iu.e510.message.board.client;

import iu.e510.message.board.db.model.DMBPost;
import iu.e510.message.board.util.Config;
import iu.e510.message.board.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        String clientID = config.getConfig(Constants.CLIENT_ID);
        // todo: remove this when building the final jar, this is for testing only
        clientID = "sachith";

        ClientService clientService = new ClientServiceImpl(clientID);

        // input processing from the standard in
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split(",");
            if (tokens.length == 1 && tokens[0].equals("stop")) {
                System.exit(0);
            }
            else if (tokens.length < 1) {
                //todo: add a comprehensive input format
                System.out.println("Wrong input format.");
                continue;
            }
            String method = tokens[0];
            processInput(clientService, tokens, method);
        }
        scanner.close();
    }

    private static void processInput(ClientService clientService, String[] tokens, String method) {
        boolean successful = false;
        switch (method) {
            case "post":
                successful = clientService.post(tokens[1].trim(), tokens[2].trim(), tokens[3].trim());
                break;
            case "reply":
                successful = clientService.replyPost(tokens[1].trim(), Integer.parseInt(tokens[2].trim()), tokens[3].trim());
                break;
            case "getposts":
                List<DMBPost> posts = clientService.getPosts(tokens[1].trim());
                for (DMBPost post : posts) {
                    System.out.println(post);
                }
            default:
                break;
        }
        if (!method.equals("getposts")) {
            if (successful) {
                System.out.println("The request was successfully executed");
            } else {
                System.out.println("Posting failed. Please try again");
            }
        }
    }
}