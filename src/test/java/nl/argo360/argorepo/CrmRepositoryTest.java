package nl.argo360.argorepo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

@SpringBootTest
class CrmRepositoryTest {
    @Autowired
    CrmRepository repository;

    @Test
    public void insert_test() {

        Contact contact = Contact.builder()
                .title("Project Manager Infrastructure Services")
                .type(Contact.Type.ONSITE)
                .firstName("John")
                .lastName("Doe")
                .email("email@test.nl")
                .phone("31609876543")
                .build();

        Account account = Account.builder()
                .companyName("Equinix")
                .accountType(Account.ACCOUNT_TYPE.ALL)
                .phone("+31612345678")
                .owner("Victor Jansma")
                .website("https://www.equinix.nl")
                .contacts(Set.of(contact))
                .build();

        repository.save(account);

        if (repository.findById("Equinix").isEmpty()) {
            fail("Account was not inserted in the database.....................");
        }

        var dbAccount = repository.findById("Equinix").get();

        assertThat(dbAccount.getCompanyName()).isEqualTo("Equinix");
        assertThat(dbAccount.getAccountType()).isEqualTo(Account.ACCOUNT_TYPE.ALL);
        assertThat(dbAccount.getPhone()).isEqualTo("+31612345678");
        assertThat(dbAccount.getOwner()).isEqualTo("Victor Jansma");
        assertThat(dbAccount.getWebsite()).isEqualTo("https://www.equinix.nl");

        if (dbAccount.getContacts().isEmpty()) {
            fail("Account does not have the expected contact................");
        }

        var dbContact = dbAccount.getContacts().stream().findFirst().get();
        assertThat(dbContact.getTitle()).isEqualTo("Project Manager Infrastructure Services");
        assertThat(dbContact.getType()).isEqualTo(Contact.Type.ONSITE);
        assertThat(dbContact.getFirstName()).isEqualTo("John");
        assertThat(dbContact.getLastName()).isEqualTo("Doe");
        assertThat(dbContact.getEmail()).isEqualTo("email@test.nl");
        assertThat(dbContact.getPhone()).isEqualTo("31609876543");
    }
}
