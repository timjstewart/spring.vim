import org.springframework.hateoas.ResourceSupport;

import PACK.domain.Xxx;
import java.util.ArrayList;

public class XxxPage
{
    private int totalElements;
    private ArrayList<Xxx> content;

    public int getTotalElements()
    {
        return totalElements;
    }

    public void setTotalElements(final int totalElements)
    {
        this.totalElements = totalElements;
    }

    public ArrayList<Xxx> getContent()
    {
        return content;
    }

    public void setContent(final ArrayList<Xxx> content)
    {
        this.content = content;
    }
}

