package com.example.cinematickets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.cinematickets.repos.MainRepository;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //adding the dummy data on startup!
        initializeDummyData();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 300);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    // creating dummy data for testing!!!
    private void initializeDummyData() {
        MainRepository repo = MainRepository.getInstance();

        System.out.println("📊 Loading dummy data...");

        // Add Halls
        repo.addHall("Hall A", 100);
        repo.addHall("Hall B", 150);
        repo.addHall("IMAX Hall", 200);
        repo.addHall("VIP Hall", 50);
        repo.addHall("3D Hall", 120);
        repo.addHall("Standard Hall 1", 80);
        repo.addHall("Standard Hall 2", 80);

        System.out.println("✓ Added 7 halls");

        // Add Movies - Action
        repo.addMovie("The Dark Knight", "English", "Action");
        repo.addMovie("Mad Max: Fury Road", "English", "Action");
        repo.addMovie("John Wick", "English", "Action");

        // Add Movies - Sci-Fi
        repo.addMovie("Inception", "English", "Sci-Fi");
        repo.addMovie("Interstellar", "English", "Sci-Fi");
        repo.addMovie("The Matrix", "English", "Sci-Fi");
        repo.addMovie("Blade Runner 2049", "English", "Sci-Fi");

        // Add Movies - Drama
        repo.addMovie("The Shawshank Redemption", "English", "Drama");
        repo.addMovie("Forrest Gump", "English", "Drama");

        // Add Movies - Thriller
        repo.addMovie("Parasite", "Korean", "Thriller");
        repo.addMovie("Gone Girl", "English", "Thriller");

        // Add Movies - Comedy
        repo.addMovie("The Grand Budapest Hotel", "English", "Comedy");
        repo.addMovie("Superbad", "English", "Comedy");

        // Add Movies - Animation
        repo.addMovie("Spirited Away", "Japanese", "Animation");
        repo.addMovie("Toy Story", "English", "Animation");
        repo.addMovie("Your Name", "Japanese", "Animation");

        // Add Movies - Horror
        repo.addMovie("The Conjuring", "English", "Horror");
        repo.addMovie("Get Out", "English", "Horror");

        // Add Movies - Crime
        repo.addMovie("The Godfather", "English", "Crime");
        repo.addMovie("Pulp Fiction", "English", "Crime");
        repo.addMovie("Goodfellas", "English", "Crime");

        System.out.println("✓ Added 21 movies");
        System.out.println("✅ Dummy data loaded successfully!");
        System.out.println("   - 7 Halls");
        System.out.println("   - 21 Movies");
        System.out.println();
    }

    public static void main(String[] args) {
        launch();
    }
}