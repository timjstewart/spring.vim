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
    /// The resource that this test will operate on.
    private Xxx xxx;

    @Autowired
    private XxxRepository xxxRepository;

    @Before
    public void setUp()
    {
        // Arrange
        final Xxx newXxx = new Xxx();

        xxx = xxxRepository.save(newXxx);
    }

    @Test
    public void canCreateXxx()
    {
        // Act was performed in the setUp() method.

        // Assert
        final Xxx foundXxx = xxxRepository.findOne(xxx.getUuid());
        assertThat(foundXxx).isNotNull();
        assertThat(foundXxx).isEqualTo(xxx);
    }

    @Test
    public void canUpdateXxx()
    {
        // Arrange
        final Xxx foundXxx = xxxRepository.findOne(xxx.getUuid());

        // Act
        // TODO: Update Xxx
        // foundXxx.set();
        xxxRepository.save(foundXxx);

        // Assert
        final Xxx updatedXxx = xxxRepository.findOne(xxx.getUuid());
        assertThat(updatedXxx).isNotNull();
        assertThat(updatedXxx).isEqualTo(foundXxx);
    }

    @Test
    public void canDeleteXxx()
    {
        // Act
        xxxRepository.delete(xxx.getUuid());

        // Assert
        final Xxx foundXxx = xxxRepository.findOne(xxx.getUuid());
        assertThat(foundXxx).isNull();
    }
}
