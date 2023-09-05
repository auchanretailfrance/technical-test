package technical.test.api.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import technical.test.api.services.LibraryService;

@RequiredArgsConstructor
@Component
public class LoadInitialData implements CommandLineRunner {

  private final LibraryService libraryService;

  @Override
  public void run(String... args) {
    libraryService.registerBook("123456", "Title1", 2023, "victor_hugo").block();
    libraryService.registerBook("12345", "Title2", 2023, "victor_hugo").block();
    libraryService.registerBook("1234", "Title3", 2023, "guillaume_musso").block();
    libraryService.registerBook("123", "Title4", 2023, "guillaume_musso").block();
    libraryService.registerBook("12", "Title5", 2023, "guillaume_musso").block();

    libraryService.registerAuthor("Victor", "Hugo", 1989).block();
    libraryService.registerAuthor("Guillaume", "Musso", 1987).block();
  }
}
