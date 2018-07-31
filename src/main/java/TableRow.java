public class TableRow {
    private String pic;
    private String rowName;
    private double size;

    public TableRow(String pic, String rowName, double size) {
        this.pic = pic;
        this.rowName = rowName;
        this.size = size;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
