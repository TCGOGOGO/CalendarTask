import java.util.Date;

/**
 * Created by tcgogogo on 16/11/7.
 */
public class AffairModel {

    private int id;
    private StringBuffer content;
    private String startDate;
    private String endData;
    private boolean state = false;

    public AffairModel() {}

    public AffairModel(int id, StringBuffer content, String startDate, String endData, boolean state) {
        this.id = id;
        this.content = content;
        this.startDate = startDate;
        this.endData = endData;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StringBuffer getContent() {
        return content;
    }

    public void setContent(StringBuffer content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    public boolean isState() {
        return state;
    }

    public int getState() {
        if (isState()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
