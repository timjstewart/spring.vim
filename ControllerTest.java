import PACK.domain.Xxx;
import PACK.presentation.XxxViewModel;
import PACK.repository.XxxRepository;
import PACK.utility.XxxPage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class XxxControllerTest {

    @Autowired
    private XxxRepository xxxRepository;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void canCreateXxx() {
        // Arrange
        final Xxx newXxx = new Xxx();
        final XxxViewModel created = template.postForObject(XxxController.ROUTE_COLLECTIVE,
                XxxViewModel.fromResource(newXxx), XxxViewModel.class);

        // Act
        final Xxx found = xxxRepository.findOne(created.getResource().getUuid());

        // Assert
        assertThat(found).isNotNull();
    }

    @Test
    public void canDeleteXxx() {
        // Arrange
        final Xxx created = xxxRepository.save(new Xxx());

        // Act
        template.delete(XxxController.ROUTE_SINGLE, created.getUuid());

        // Assert
        final Xxx found = xxxRepository.findOne(created.getUuid());
        assertThat(found).isNull();
    }

    @Test
    public void canGetOneXxx() {
        // Arrange
        final Xxx created = xxxRepository.save(new Xxx());

        // Act
        final XxxViewModel found = template.getForObject(XxxController.ROUTE_SINGLE,
                XxxViewModel.class, created.getUuid());

        // Assert
        assertThat(found).isNotNull();
    }

    @Test
    public void canGetMultipleXxxs() {
        // Arrange
        final int xxxCount = template.getForObject(XxxController.ROUTE_COLLECTIVE, XxxPage.class).getTotalElements();

        final Xxx newXxx = new Xxx();
        final Xxx created = xxxRepository.save(newXxx);

        // Act
        final XxxPage xxxs = template.getForObject(XxxController.ROUTE_COLLECTIVE, XxxPage.class);

        // Assert
        assertThat(xxxs.getTotalElements()).isEqualTo(xxxCount + 1);
    }

    @Test
    public void canUpdateXxx() {
        // Arrange
        final Xxx created = xxxRepository.save(new Xxx());

        // Act
        // TODO: Modify created...
        template.put(XxxController.ROUTE_SINGLE, XxxViewModel.fromResource(created), created.getUuid());

        // Assert
        final Xxx found = xxxRepository.findOne(created.getUuid());
        assertThat(found).isEqualTo(created);
    }
}
