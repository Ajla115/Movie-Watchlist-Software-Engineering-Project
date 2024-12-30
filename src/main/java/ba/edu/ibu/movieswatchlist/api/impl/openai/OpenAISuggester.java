package ba.edu.ibu.movieswatchlist.api.impl.openai;//package ba.edu.ibu.movieswatchlist.api.impl.openai;
//import ba.edu.ibu.movieswatchlist.core.api.genresuggester.GenreSuggester;
//import com.theokanning.openai.completion.CompletionRequest;
//import com.theokanning.openai.service.OpenAiService;
//
//public class OpenAISuggester implements GenreSuggester {
//    private final OpenAiService openAiService;
//
//    public OpenAISuggester(OpenAiService openAiService) {
//        this.openAiService = openAiService;
//    }
//
//    @Override
//    public String suggestGenre(String title) {
//        String prompt = "Suggest a genre for the following movie title: " + title;
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt(prompt)
//                .model("gpt-3.5-turbo-instruct")
//                .maxTokens(10)
//                .build();
//        return openAiService.createCompletion(completionRequest).getChoices().get(0).getText().trim();
//    }
//}

import ba.edu.ibu.movieswatchlist.core.api.genresuggester.GenreSuggester;
import ba.edu.ibu.movieswatchlist.core.model.Genre;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
//
//import java.util.List;
//
//public class OpenAISuggester implements GenreSuggester {
//    private final OpenAiService openAiService;
//
//    public OpenAISuggester(OpenAiService openAiService) {
//        this.openAiService = openAiService;
//    }
//
//    @Override
//    public String suggestGenre(String title, List<Genre> availableGenres) {
//        String prompt = "Suggest the most suitable genre from the following list: "
//                + availableGenres.stream()
//                .map(Genre::getName)
//                .reduce((a, b) -> a + ", " + b)
//                .orElse("")
//                + " for the movie title: " + title;
//
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt(prompt)
//                .model("gpt-3.5-turbo-instruct")
//                .maxTokens(20) // Increase token limit for more elaborate suggestions
//                .build();
//
//        String aiResponse = openAiService.createCompletion(completionRequest).getChoices().get(0).getText().trim();
//
//        // Match the AI response to the available genres
//        return availableGenres.stream()
//                .filter(genre -> aiResponse.equalsIgnoreCase(genre.getName()))
//                .map(Genre::getName)
//                .findFirst()
//                .orElse("Unknown Genre"); // Default value if no match is found
//    }
//}

@Service
public class OpenAISuggester implements GenreSuggester {
    private final OpenAiService openAiService;

    public OpenAISuggester(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }
    @Override
    public String suggestGenre(String title) {
        String prompt = "Suggest a movie genre for the title: " + title;
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model("gpt-3.5-turbo-instruct") // Use a suitable model
                .maxTokens(10)
                .build();

        try {
            return openAiService.createCompletion(completionRequest)
                    .getChoices()
                    .get(0)
                    .getText()
                    .trim();

        } catch (Exception e) {
            // Log the error and return a user-friendly error message
            System.err.println("OpenAI API error: " + e.getMessage());
            return "Error: OpenAI API is overwhelmed at the moment. Please try again later.";
        }
    }


    @Override
    public String suggestMovies(String genre) {
        String prompt = "Suggest at least one good movie from this genre: " + genre;
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model("gpt-3.5-turbo-instruct") // Use a suitable model
                .maxTokens(10)
                .build();

        try {
            return openAiService.createCompletion(completionRequest)
                    .getChoices()
                    .get(0)
                    .getText()
                    .trim();

        } catch (Exception e) {
            System.err.println("OpenAI API error: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve movie suggestions", e);
        }
    }
}