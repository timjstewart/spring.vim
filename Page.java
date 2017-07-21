import PACK.presentation.XxxViewModel;
import java.util.ArrayList;

public class XxxPage
{
    private int totalElements;
    private ArrayList<XxxViewModel> content;

    public int getTotalElements()
    {
        return totalElements;
    }

    public void setTotalElements(final int totalElements)
    {
        this.totalElements = totalElements;
    }

    public ArrayList<XxxViewModel> getContent()
    {
        return content;
    }

    public void setContent(final ArrayList<XxxViewModel> content)
    {
        this.content = content;
    }
}

