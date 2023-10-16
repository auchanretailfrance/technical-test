package technical.test.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import technical.test.api.usecases.author.RegisterAuthor;
import technical.test.api.usecases.book.RegisterBook;

@Component
public class TestSupport {
    @Autowired
    RegisterBook registerBook;
    @Autowired
    RegisterAuthor registerAuthor;
    public Mono<Void> loadBooks() {
        return Mono.empty()
                .then(registerBook.register("1234-5678-90","Fondation", 1951, "isaac_asimov"))
                .then(registerBook.register("1234-5678-91","Fondation et Empire", 1952, "isaac_asimov"))
                .then(registerBook.register("1234-5678-92","Seconde Fondation", 1953, "isaac_asimov"))
                .then(registerBook.register("1234-5678-93","Fondation foudroyée", 1982, "isaac_asimov"))
                .then(registerBook.register("1234-5678-94","Terre et Fondation", 1986, "isaac_asimov"))
                .then(registerBook.register("1234-5678-95","Prélude à Fondation", 1988, "isaac_asimov"))
                .then(registerBook.register("1234-5678-96","L'Aube de Fondation", 1993, "isaac_asimov"))
                .then(registerBook.register("2345-6789-01", "Les Robots", 1950, "isaac_asimov"))
                .then(registerBook.register("2345-6789-02", "Un défilé de robots", 1964, "isaac_asimov"))
                .then(registerBook.register("2345-6789-03", "Les Cavernes d'acier", 1954, "isaac_asimov"))
                .then(registerBook.register("2345-6789-04", "Face aux feux du soleil", 1957, "isaac_asimov"))
                .then(registerBook.register("2345-6789-05", "Les Robots de l'aube", 1983, "isaac_asimov"))
                .then(registerBook.register("2345-6789-06", "Les Robots et l'Empire", 1985, "isaac_asimov"))
                .then(registerBook.register("3456-7890-01", "Le Guide du voyageur galactique", 1979, "douglas_adams"))
                .then();
    }

    public Mono<Void> loadAuthor() {
        return Mono.empty()
                .then(registerAuthor.register("Isaac","Asimov", 1920))
                .then(registerAuthor.register("Peter F.","Hamilton", 1960))
                .then(registerAuthor.register("Michael","Moorcock", 1939))
                .then(registerAuthor.register("Douglas","Adams", 1939))
                .then();
    }
}
