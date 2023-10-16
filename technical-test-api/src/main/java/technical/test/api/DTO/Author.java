package technical.test.api.DTO;

import lombok.Getter;

@Getter
public class Author {

  private final String id;
  private final String firstName;
  private final String lastName;
  private final int birthDate;

  public Author(String firstName, String lastName, int birthDate) {
    this.id = String.format("%s_%s", firstName, lastName).toLowerCase();
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
  }

}
