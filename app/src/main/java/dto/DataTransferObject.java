package dto;

/**
 * Created by Miqueias on 1/23/17.
 */

public class DataTransferObject {

    private static DataTransferObject instance = new DataTransferObject();
    private Object dto;

    public static synchronized DataTransferObject getInstance() {
        if (instance == null) {
            instance = new DataTransferObject();
        }
        return instance;
    }

    private DataTransferObject() {

    }

    public Object getDto() {
        return dto;
    }

    public void setDto(Object dto) {
        this.dto = dto;
    }
}
