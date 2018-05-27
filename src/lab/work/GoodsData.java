package lab.work;


public class GoodsData {

    private int id;
    private String name;


    GoodsData(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
