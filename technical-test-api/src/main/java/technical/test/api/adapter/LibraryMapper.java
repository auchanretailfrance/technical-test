package technical.test.api.adapter;

import org.springframework.stereotype.Component;
import technical.test.api.domain.ISBN;

import java.time.Year;

@Component
public class LibraryMapper {
    public ISBN map(String value) {
        return ISBN.of(value);
    }

    public String map(ISBN value) {
        return value.id();
    }

    public Year map(Integer value) {
        return Year.of(value);
    }

    public Integer map(Year value) {
        return value.getValue();
    }
}
