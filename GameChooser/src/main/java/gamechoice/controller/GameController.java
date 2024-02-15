package gamechoice.controller;

import gamechoice.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final List<String> displayedGames = new ArrayList<>();
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/fetchRandom")
    public void fetchRandomGameAndAskForApproval() {
        Scanner scanner = new Scanner(System.in);
        String userResponse;

        try {
            do {
                // fetching a random game not already displayed
                String randomGame;
                do {
                    randomGame = gameService.fetchRandomGame();
                } while (displayedGames.contains(randomGame));

                System.out.println("Random Game Details:");
                System.out.println(randomGame);


                System.out.print("Are you satisfied with this choice? (yes/no): ");
                System.out.flush(); // Flush the output stream
                userResponse = scanner.nextLine().toLowerCase();


                if (userResponse.equals("no")) {
                    displayedGames.add(randomGame);
                    System.out.println(displayedGames);
                }

                System.out.println("User response: " + userResponse);

            } while (!userResponse.equals("yes"));

            System.out.println("Great choice! Enjoy your game.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/start")
    public void startGameSelectionProcess() {
        fetchRandomGameAndAskForApproval();
    }
}
