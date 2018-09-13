public class TableRow {
    private CustomImage pic;
    private String rowName;
    private String size;

    public TableRow(CustomImage pic, String rowName, String size) {
        this.pic = pic;
        this.rowName = rowName;
        this.size = size;
    }

    public CustomImage getPic() {
        return pic;
    }

    public void setPic(CustomImage pic) {
        this.pic = pic;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
