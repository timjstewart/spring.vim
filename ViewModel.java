import java.util.Objects;

import PACK.domain.Xxx;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ResourceSupport;

public class XxxViewModel extends ResourceSupport
{
    @JsonUnwrapped
    private Xxx xxx;

    public XxxViewModel()
    {
        // Required by Jackson
    }

    public Xxx getXxx()
    {
        return xxx;
    }

    public void setXxx(final Xxx xxx)
    {
        this.xxx = xxx;
    }

    /**
     * Converts a page of Xxx objects into a page of XxxViewModel objects.
     *
     * @param page the Page of Xxx objects to convert.
     *
     * @return the XxxViewModel.
     */
    public static Page<XxxViewModel> fromResources(final Page<Xxx> page)
    {
        Objects.requireNonNull(page, "page cannot be null");

        return page.map(XxxViewModel::fromResource);
    }

    /**
     * Converts an Xxx object into an XxxViewModel object.
     *
     * @param xxx the Xxx object to convert.
     *
     * @return the XxxViewModel.
     */
    public static XxxViewModel fromResource(final Xxx xxx)
    {
        Objects.requireNonNull(xxx, "xxx cannot be null");

        final XxxViewModel model = new XxxViewModel();

        model.setXxx(xxx);

        return model;
    }

    /**
     * Converts an XxxViewModel to an Xxx object.
     *
     * Most of the time, this just requires unwrapping the Xxx from inside the XxxViewModel.
     *
     * @return the Xxx resource.
     */
    @JsonIgnore
    public Xxx getResource()
    {
        return xxx;
    }
}

