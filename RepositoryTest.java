import PACK.domain.Xxx;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxxRepositoryTest
{

    @Autowired
    private XxxRepository repository;

    @Before
    public void setUp()
    {
        repository.deleteAll();
    }

    @Test
    public void canCreateXxx()
    {
        // Arrange
        final Xxx xxx = new Xxx();

        // Act
        final Xxx createdXxx = repository.save(xxx);

        // Assert
        final Xxx foundXxx = repository.findOne(createdXxx.getUuid());
        assertThat(foundXxx).isNotNull();
        assertThat(foundXxx).isEqualTo(createdXxx);
    }

    @Test
    public void canUpdateXxx()
    {
        // Arrange
        final Xxx xxx = new Xxx();
        final Xxx createdXxx = repository.save(xxx);
        final Xxx foundXxx = repository.findOne(createdXxx.getUuid());

        // Act
        // TODO: Update Xxx
        // foundXxx.set();
        repository.save(foundXxx);

        // Assert
        final Xxx updatedXxx = repository.findOne(createdXxx.getUuid());
        assertThat(updatedXxx).isNotNull();
        assertThat(updatedXxx).isEqualTo(foundXxx);
    }

    @Test
    public void canDeleteXxx()
    {
        // Arrange
        final Xxx xxx = new Xxx();
        final Xxx createdXxx = repository.save(xxx);

        // Act
        repository.delete(createdXxx.getUuid());

        // Assert
        final Xxx foundXxx = repository.findOne(createdXxx.getUuid());
        assertThat(foundXxx).isNull();
    }
}
