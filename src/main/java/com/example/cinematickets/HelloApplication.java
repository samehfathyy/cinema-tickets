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

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("movie-selection.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Hello!");
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

        // Add ShowTimes - create showtimes for each movie in different halls
        // Action movies
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 25, 14, 30), 25.0f, 0, 0); // Dark Knight in Hall A
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 25, 19, 00), 30.0f, 0, 1); // Dark Knight in Hall B
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 26, 16, 00), 28.0f, 1, 2); // Mad Max in IMAX
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 26, 21, 30), 32.0f, 2, 3); // John Wick in VIP

        // Sci-Fi movies
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 27, 15, 15), 35.0f, 3, 2); // Inception in IMAX
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 27, 20, 45), 40.0f, 4, 3); // Interstellar in VIP
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 28, 13, 30), 30.0f, 5, 4); // Matrix in 3D
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 28, 18, 00), 38.0f, 6, 2); // Blade Runner in IMAX

        // Drama movies
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 29, 17, 00), 22.0f, 7, 0); // Shawshank in Hall A
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 29, 14, 30), 25.0f, 8, 1); // Forrest Gump in Hall B

        // Thriller movies
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 30, 19, 30), 28.0f, 9, 4); // Parasite in 3D
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 30, 16, 15), 26.0f, 10, 5); // Gone Girl in Standard 1

        // Comedy movies
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 31, 15, 00), 24.0f, 11, 5); // Grand Budapest in Standard 1
        repo.addShowTime(java.time.LocalDateTime.of(2024, 12, 31, 20, 30), 27.0f, 12, 6); // Superbad in Standard 2

        // Animation movies
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 1, 12, 00), 20.0f, 13, 0); // Spirited Away in Hall A
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 1, 15, 30), 18.0f, 14, 1); // Toy Story in Hall B
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 2, 14, 00), 22.0f, 15, 2); // Your Name in IMAX

        // Horror movies
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 2, 22, 00), 25.0f, 16, 4); // Conjuring in 3D
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 3, 21, 15), 23.0f, 17, 5); // Get Out in Standard 1

        // Crime movies
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 3, 18, 45), 30.0f, 18, 6); // Godfather in Standard 2
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 4, 16, 30), 28.0f, 19, 3); // Pulp Fiction in VIP
        repo.addShowTime(java.time.LocalDateTime.of(2025, 1, 4, 20, 00), 32.0f, 20, 2); // Goodfellas in IMAX

        System.out.println("✓ Added 21 showtimes");
        System.out.println("✅ Dummy data loaded successfully!");
        System.out.println("   - 7 Halls");
        System.out.println("   - 21 Movies");
        System.out.println("   - 21 Showtimes");
        System.out.println();
    }

    public static void main(String[] args) {
        launch();
    }
}